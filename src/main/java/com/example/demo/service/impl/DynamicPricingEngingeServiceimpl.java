// 2. Service Implementation
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRecordRepository;
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final DynamicPriceRecordRepository dynamicPriceRecordRepository;
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRecordRepository,
            SeatInventoryRecordRepository seatInventoryRecordRepository,
            PricingRuleRepository pricingRuleRepository,
            DynamicPriceRecordRepository dynamicPriceRecordRepository,
            PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
        this.eventRecordRepository = eventRecordRepository;
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.pricingRuleRepository = pricingRuleRepository;
        this.dynamicPriceRecordRepository = dynamicPriceRecordRepository;
        this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        // Fetch event
        EventRecord event = eventRecordRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));
        
        // Check if event is active
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
        // Fetch seat inventory
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new NotFoundException("Seat inventory not found for event id: " + eventId));
        
        // Calculate days before event
        long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
        // Get active pricing rules
        List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
        // Find applicable rule based on remaining seats and days before event
        PricingRule applicableRule = findApplicableRule(
                inventory.getRemainingSeats(), 
                daysBeforeEvent, 
                activeRules
        );
        
        // Calculate dynamic price
        double dynamicPrice = calculateDynamicPrice(event.getBasePrice(), applicableRule);
        
        // Create dynamic price record
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setBasePrice(event.getBasePrice());
        priceRecord.setRemainingSeats(inventory.getRemainingSeats());
        priceRecord.setDaysBeforeEvent((int) daysBeforeEvent);
        priceRecord.setComputedPrice(dynamicPrice);
        
        if (applicableRule != null) {
            priceRecord.setAppliedRuleCodes(applicableRule.getRuleCode());
            priceRecord.setAppliedMultiplier(applicableRule.getPriceMultiplier());
        } else {
            priceRecord.setAppliedRuleCodes("NONE");
            priceRecord.setAppliedMultiplier(1.0);
        }
        
        // Check if price changed from previous computation
        checkAndLogPriceChange(eventId, dynamicPrice);
        
        // Save and return
        return dynamicPriceRecordRepository.save(priceRecord);
    }
    
    private PricingRule findApplicableRule(int remainingSeats, long daysBeforeEvent, List<PricingRule> rules) {
        for (PricingRule rule : rules) {
            if (rule.getActive() &&
                remainingSeats >= rule.getMinRemainingSeats() &&
                remainingSeats <= rule.getMaxRemainingSeats() &&
                daysBeforeEvent <= rule.getDaysBeforeEvent()) {
                return rule;
            }
        }
        return null;
    }
    
    private double calculateDynamicPrice(double basePrice, PricingRule rule) {
        if (rule == null) {
            return basePrice;
        }
        return basePrice * rule.getPriceMultiplier();
    }
    
    private void checkAndLogPriceChange(Long eventId, double newPrice) {
        dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId)
                .ifPresent(previousRecord -> {
                    if (Math.abs(previousRecord.getComputedPrice() - newPrice) > 0.01) {
                        // Price changed, log the adjustment
                        PriceAdjustmentLog log = new PriceAdjustmentLog();
                        log.setEventId(eventId);
                        log.setOldPrice(previousRecord.getComputedPrice());
                        log.setNewPrice(newPrice);
                        log.setChangeReason("Dynamic pricing recomputation");
                        priceAdjustmentLogRepository.save(log);
                    }
                });
    }
    
    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }
    
    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicPriceRecordRepository.findAll();
    }
}
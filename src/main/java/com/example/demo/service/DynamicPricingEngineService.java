package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicPricingEngineService {
    
    private final EventRecordRepository eventRecordRepository;
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final DynamicPriceRecordRepository dynamicPriceRecordRepository;
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
    
    public DynamicPricingEngineService(
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
    
    @Transactional
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        // Fetch event
        EventRecord event = eventRecordRepository.findById(eventId)
            .orElseThrow(() -> new BadRequestException("Event not found"));
        
        // Check if event is active
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
        // Fetch inventory
        SeatInventoryRecord inventory = seatInventoryRecordRepository
            .findByEventId(eventId)
            .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
        
        // Get active pricing rules
        List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
        // Calculate days before event
        long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
        // Apply matching rules
        List<String> appliedRuleCodes = new ArrayList<>();
        double computedPrice = event.getBasePrice();
        
        for (PricingRule rule : activeRules) {
            boolean matches = true;
            
            // Check seat range
            if (rule.getMinRemainingSeats() != null) {
                if (inventory.getRemainingSeats() < rule.getMinRemainingSeats()) {
                    matches = false;
                }
            }
            
            if (rule.getMaxRemainingSeats() != null && matches) {
                if (inventory.getRemainingSeats() > rule.getMaxRemainingSeats()) {
                    matches = false;
                }
            }
            
            // Check days before event
            if (rule.getDaysBeforeEvent() != null && matches) {
                if (daysBeforeEvent > rule.getDaysBeforeEvent()) {
                    matches = false;
                }
            }
            
            if (matches) {
                appliedRuleCodes.add(rule.getRuleCode());
                computedPrice *= rule.getPriceMultiplier();
            }
        }
        
        // Round to 2 decimal places
        computedPrice = Math.round(computedPrice * 100.0) / 100.0;
        
        // Validate computed price
        if (computedPrice <= 0) {
            throw new BadRequestException("Computed price must be > 0");
        }
        
        // Create DynamicPriceRecord
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setComputedPrice(computedPrice);
        priceRecord.setAppliedRuleCodes(String.join(",", appliedRuleCodes));
        DynamicPriceRecord savedRecord = dynamicPriceRecordRepository.save(priceRecord);
        
        // Check for price change and log if material
        Optional<DynamicPriceRecord> latestPrice = dynamicPriceRecordRepository
            .findFirstByEventIdOrderByComputedAtDesc(eventId);
        
        if (latestPrice.isPresent() && latestPrice.get().getId() != null) {
            double oldPrice = latestPrice.get().getComputedPrice();
            double newPrice = savedRecord.getComputedPrice();
            
            // Log if price changed by more than 1%
            if (Math.abs(newPrice - oldPrice) / oldPrice > 0.01) {
                PriceAdjustmentLog log = new PriceAdjustmentLog();
                log.setEventId(eventId);
                log.setOldPrice(oldPrice);
                log.setNewPrice(newPrice);
                log.setReason("Dynamic pricing adjustment based on seat availability and timing");
                priceAdjustmentLogRepository.save(log);
            }
        }
        
        return savedRecord;
    }
    
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }
    
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }
    
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicPriceRecordRepository.findAll();
    }
}







// package com.example.demo.service;

// import com.example.demo.model.DynamicPriceRecord;

// import java.util.List;
// import java.util.Optional;

// public interface DynamicPricingEngineService {

//     DynamicPriceRecord computeDynamicPrice(Long eventId);

//     List<DynamicPriceRecord> getPriceHistory(Long eventId);

//     Optional<DynamicPriceRecord> getLatestPrice(Long eventId);

//     List<DynamicPriceRecord> getAllComputedPrices();
// }

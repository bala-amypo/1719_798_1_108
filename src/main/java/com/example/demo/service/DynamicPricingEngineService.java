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
        // 1. Fetch and validate event
        EventRecord event = eventRecordRepository.findById(eventId)
            .orElseThrow(() -> new BadRequestException("Event not found"));
        
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
        // 2. Fetch inventory
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
            .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
        
        // 3. Get active pricing rules
        List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
        // 4. Calculate days before event
        long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
        // 5. Apply matching rules
        double computedPrice = event.getBasePrice();
        List<String> appliedRuleCodes = new ArrayList<>();
        
        for (PricingRule rule : activeRules) {
            if (matchesRule(rule, inventory.getRemainingSeats(), daysBeforeEvent)) {
                computedPrice *= rule.getPriceMultiplier();
                appliedRuleCodes.add(rule.getRuleCode());
            }
        }
        
        // 6. Validate computed price
        if (computedPrice <= 0) {
            throw new BadRequestException("Computed price must be > 0");
        }
        
        // 7. Get previous price
        Double previousPrice = null;
        Optional<DynamicPriceRecord> latestPrice = dynamicPriceRecordRepository
            .findFirstByEventIdOrderByComputedAtDesc(eventId);
        
        if (latestPrice.isPresent()) {
            previousPrice = latestPrice.get().getComputedPrice();
        }
        
        // 8. Save dynamic price record
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setComputedPrice(computedPrice);
        priceRecord.setAppliedRuleCodes(String.join(",", appliedRuleCodes));
        DynamicPriceRecord savedRecord = dynamicPriceRecordRepository.save(priceRecord);
        
        // 9. Log adjustment if price changed materially (more than 1%)
        if (previousPrice != null && Math.abs(computedPrice - previousPrice) / previousPrice > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice);
            log.setNewPrice(computedPrice);
            log.setReason("Dynamic pricing adjustment based on " + appliedRuleCodes.size() + " rules");
            priceAdjustmentLogRepository.save(log);
        }
        
        return savedRecord;
    }
    
    private boolean matchesRule(PricingRule rule, int remainingSeats, long daysBeforeEvent) {
        boolean matches = true;
        
        if (rule.getMinRemainingSeats() != null) {
            matches = matches && remainingSeats >= rule.getMinRemainingSeats();
        }
        
        if (rule.getMaxRemainingSeats() != null) {
            matches = matches && remainingSeats <= rule.getMaxRemainingSeats();
        }
        
        if (rule.getDaysBeforeEvent() != null) {
            matches = matches && daysBeforeEvent <= rule.getDaysBeforeEvent();
        }
        
        return matches;
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

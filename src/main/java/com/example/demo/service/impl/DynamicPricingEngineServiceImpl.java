package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    @Transactional
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        // Get event
        EventRecord event = eventRecordRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));
        
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
        // Get seat inventory
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new NotFoundException("Seat inventory not found for event id: " + eventId));
        
        // Get all active pricing rules
        List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
        // Calculate days before event
        long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
        // Compute dynamic price
        double computedPrice = event.getBasePrice();
        List<String> appliedRules = new ArrayList<>();
        
        for (PricingRule rule : activeRules) {
            if (rule.getDaysBeforeEvent() != null && daysBeforeEvent > rule.getDaysBeforeEvent()) {
                continue;
            }
            
            if (rule.getMinRemainingSeats() != null && inventory.getRemainingSeats() < rule.getMinRemainingSeats()) {
                continue;
            }
            
            if (rule.getMaxRemainingSeats() != null && inventory.getRemainingSeats() > rule.getMaxRemainingSeats()) {
                continue;
            }
            
            computedPrice *= rule.getPriceMultiplier();
            appliedRules.add(rule.getRuleCode());
        }
        
        // Get previous price
        Double oldPrice = null;
        DynamicPriceRecord previous = dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId)
                .orElse(null);
        
        if (previous != null) {
            oldPrice = previous.getComputedPrice();
        }
        
        // Create new dynamic price record
        DynamicPriceRecord dynamicPrice = new DynamicPriceRecord();
        dynamicPrice.setEventId(eventId);
        dynamicPrice.setComputedPrice(computedPrice);
        dynamicPrice.setAppliedRuleCodes(String.join(",", appliedRules));
        
        DynamicPriceRecord saved = dynamicPriceRecordRepository.save(dynamicPrice);
        
        // Log price adjustment if price changed
        if (oldPrice != null && Math.abs(oldPrice - computedPrice) > 0.01) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(oldPrice);
            log.setNewPrice(computedPrice);
            log.setReason("Dynamic pricing adjustment based on rules: " + String.join(",", appliedRules));
            priceAdjustmentLogRepository.save(log);
        }
        
        return saved;
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












// package com.example.demo.service.impl;

// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.DynamicPriceRecordService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.List;
// import java.util.Optional;
// @Service
// public class DynamicPriceRecordImpl implements DynamicPriceRecordService {
    
//     private final EventRecordRepository eventRecordRepository;
//     private final SeatInventoryRecordRepository inventoryRepository;
//     private final PricingRuleRepository pricingRuleRepository;
//     private final DynamicPriceRecordRepository dynamicPriceRepository;
//     private final PriceAdjustmentLogRepository adjustmentLogRepository;
    
//     @Autowired
//     public DynamicPriceRecordImpl(EventRecordRepository eventRecordRepository,
//                                  SeatInventoryRecordRepository inventoryRepository,
//                                  PricingRuleRepository pricingRuleRepository,
//                                  DynamicPriceRecordRepository dynamicPriceRepository,
//                                  PriceAdjustmentLogRepository adjustmentLogRepository) {
//         this.eventRecordRepository = eventRecordRepository;
//         this.inventoryRepository = inventoryRepository;
//         this.pricingRuleRepository = pricingRuleRepository;
//         this.dynamicPriceRepository = dynamicPriceRepository;
//         this.adjustmentLogRepository = adjustmentLogRepository;
//     }
    
//     @Override
//     public DynamicPriceRecord computeDynamicPrice(Long eventId) {
//         // Get event
//         EventRecord event = eventRecordRepository.findById(eventId)
//                 .orElseThrow(() -> new RuntimeException("Event not found"));
        
//         // Check if event is active
//         if (!event.getActive()) {
//             throw new RuntimeException("Event is not active");
//         }
        
//         // Get inventory
//         Optional<SeatInventoryRecord> inventoryOpt = inventoryRepository.findByEventId(eventId);
//         if (!inventoryOpt.isPresent()) {
//             throw new RuntimeException("Seat inventory not found");
//         }
        
//         SeatInventoryRecord inventory = inventoryOpt.get();
        
//         // Get active rules
//         List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
//         // Calculate days before event
//         long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        
//         // Apply rules
//         Double finalPrice = event.getBasePrice();
//         StringBuilder appliedRules = new StringBuilder();
        
//         for (PricingRule rule : activeRules) {
//             // Check seat range
//             boolean seatMatch = true;
//             if (rule.getMinRemainingSeats() != null) {
//                 seatMatch = seatMatch && (inventory.getRemainingSeats() >= rule.getMinRemainingSeats());
//             }
//             if (rule.getMaxRemainingSeats() != null) {
//                 seatMatch = seatMatch && (inventory.getRemainingSeats() <= rule.getMaxRemainingSeats());
//             }
            
//             // Check days before event
//             boolean dateMatch = true;
//             if (rule.getDaysBeforeEvent() != null) {
//                 dateMatch = daysBeforeEvent <= rule.getDaysBeforeEvent();
//             }
            
//             // Apply multiplier if both conditions match
//             if (seatMatch && dateMatch) {
//                 finalPrice *= rule.getPriceMultiplier();
//                 if (appliedRules.length() > 0) {
//                     appliedRules.append(",");
//                 }
//                 appliedRules.append(rule.getRuleCode());
//             }
//         }
        
//         // Create dynamic price record
//         DynamicPriceRecord priceRecord = new DynamicPriceRecord(eventId, finalPrice);
//         priceRecord.setAppliedRuleCodes(appliedRules.toString());
        
//         // Get previous price
//         Optional<DynamicPriceRecord> previousPriceOpt = dynamicPriceRepository
//                 .findFirstByEventIdOrderByComputedAtDesc(eventId);
        
//         // Save the new price
//         DynamicPriceRecord savedRecord = dynamicPriceRepository.save(priceRecord);
        
//         // Log adjustment if price changed
//         if (previousPriceOpt.isPresent()) {
//             Double previousPrice = previousPriceOpt.get().getComputedPrice();
//             if (Math.abs(previousPrice - finalPrice) > 0.01) { // Material change
//                 PriceAdjustmentLog log = new PriceAdjustmentLog(
//                     eventId,
//                     previousPrice,
//                     finalPrice,
//                     "Dynamic pricing adjustment"
//                 );
//                 adjustmentLogRepository.save(log);
//             }
//         }
        
//         return savedRecord;
//     }
    
//     @Override
//     public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
//         return dynamicPriceRepository.findByEventIdOrderByComputedAtDesc(eventId);
//     }
    
//     @Override
//     public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
//         return dynamicPriceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
//     }
    
//     @Override
//     public List<DynamicPriceRecord> getAllComputedPrices() {
//         return dynamicPriceRepository.findAll();
//     }
// }
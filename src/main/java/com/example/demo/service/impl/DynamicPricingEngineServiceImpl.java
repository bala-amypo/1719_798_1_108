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
       
        EventRecord event = eventRecordRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));
        
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
       
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new NotFoundException("Seat inventory not found for event id: " + eventId));
      
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
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.NotFoundException;
// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.DynamicPricingEngineService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.ArrayList;
// import java.util.List;
// @Service
// public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {
//     private final EventRecordRepository eventRecordRepository;
//     private final SeatInventoryRecordRepository seatInventoryRecordRepository;
//     private final PricingRuleRepository pricingRuleRepository;
//     private final DynamicPriceRecordRepository dynamicPriceRecordRepository;
//     private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
//     public DynamicPricingEngineServiceImpl(
//             EventRecordRepository eventRecordRepository,
//             SeatInventoryRecordRepository seatInventoryRecordRepository,
//             PricingRuleRepository pricingRuleRepository,
//             DynamicPriceRecordRepository dynamicPriceRecordRepository,
//             PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
//         this.eventRecordRepository = eventRecordRepository;
//         this.seatInventoryRecordRepository = seatInventoryRecordRepository;
//         this.pricingRuleRepository = pricingRuleRepository;
//         this.dynamicPriceRecordRepository = dynamicPriceRecordRepository;
//         this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
//     }
//     @Override
//     @Transactional
//     public DynamicPriceRecord computeDynamicPrice(Long eventId) {
//         EventRecord event = eventRecordRepository.findById(eventId)
//                 .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));   
//         if (!event.getActive()) {
//             throw new BadRequestException("Event is not active");
//         }
//         SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
//                 .orElseThrow(() -> new NotFoundException("Seat inventory not found for event id: " + eventId));
//         List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
//         long daysBeforeEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
//         double computedPrice = event.getBasePrice();
//         List<String> appliedRules = new ArrayList<>();
//         for (PricingRule rule : activeRules) {
//             if (rule.getDaysBeforeEvent() != null && daysBeforeEvent > rule.getDaysBeforeEvent()) {
//                 continue;
//             }
//             if (rule.getMinRemainingSeats() != null && inventory.getRemainingSeats() < rule.getMinRemainingSeats()) {
//                 continue;
//             }
//             if (rule.getMaxRemainingSeats() != null && inventory.getRemainingSeats() > rule.getMaxRemainingSeats()) {
//                 continue;
//             }
//             computedPrice *= rule.getPriceMultiplier();
//             appliedRules.add(rule.getRuleCode());
//         }
//         Double oldPrice = null;
//         DynamicPriceRecord previous = dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId) .orElse(null);
//         if (previous != null) {
//             oldPrice = previous.getComputedPrice();
//         }
//         DynamicPriceRecord dynamicPrice = new DynamicPriceRecord();
//         dynamicPrice.setEventId(eventId);
//         dynamicPrice.setComputedPrice(computedPrice);
//         dynamicPrice.setAppliedRuleCodes(String.join(",", appliedRules));
//         DynamicPriceRecord saved = dynamicPriceRecordRepository.save(dynamicPrice);
//         if (oldPrice != null && Math.abs(oldPrice - computedPrice) > 0.01) {
//             PriceAdjustmentLog log = new PriceAdjustmentLog();
//             log.setEventId(eventId);
//             log.setOldPrice(oldPrice);
//             log.setNewPrice(computedPrice);
//             log.setReason("Dynamic pricing adjustment based on rules: " + String.join(",", appliedRules));
//             priceAdjustmentLogRepository.save(log);
//         }
//         return saved;
//     }
//     @Override
//     public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
//         return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
//     }
//     @Override
//     public List<DynamicPriceRecord> getAllComputedPrices() {
//         return dynamicPriceRecordRepository.findAll();
//     }
// }
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }
    
    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }
    
    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicPriceRecordRepository.findAll();
    }
}



// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.DynamicPricingEngineService;
// import org.springframework.stereotype.Service;

// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

//     private final EventRecordRepository eventRepository;
//     private final SeatInventoryRecordRepository inventoryRepository;
//     private final PricingRuleRepository ruleRepository;
//     private final DynamicPriceRecordRepository priceRepository;
//     private final PriceAdjustmentLogRepository logRepository;

//     public DynamicPricingEngineServiceImpl(
//             EventRecordRepository eventRepository,
//             SeatInventoryRecordRepository inventoryRepository,
//             PricingRuleRepository ruleRepository,
//             DynamicPriceRecordRepository priceRepository,
//             PriceAdjustmentLogRepository logRepository) {
//         this.eventRepository = eventRepository;
//         this.inventoryRepository = inventoryRepository;
//         this.ruleRepository = ruleRepository;
//         this.priceRepository = priceRepository;
//         this.logRepository = logRepository;
//     }

//     @Override
//     public DynamicPriceRecord computeDynamicPrice(Long eventId) {
//         EventRecord event = eventRepository.findById(eventId)
//                 .orElseThrow(() -> new BadRequestException("Event not found"));

//         if (!Boolean.TRUE.equals(event.getActive())) {
//             throw new BadRequestException("Event is not active");
//         }

//         SeatInventoryRecord inventory = inventoryRepository.findByEvent_Id(eventId)
//                 .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

//         List<PricingRule> rules = ruleRepository.findByActiveTrue();
//         double multiplier = 1.0;
//         String appliedRules = "";

//         long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());

//         for (PricingRule rule : rules) {
//             if (inventory.getRemainingSeats() >= rule.getMinRemainingSeats()
//                     && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats()
//                     && daysToEvent <= rule.getDaysBeforeEvent()) {

//                 if (rule.getPriceMultiplier() > multiplier) {
//                     multiplier = rule.getPriceMultiplier();
//                     appliedRules = rule.getRuleCode();
//                 }
//             }
//         }

//         double newPrice = event.getBasePrice() * multiplier;

//         DynamicPriceRecord record = new DynamicPriceRecord(null, event, newPrice, appliedRules);

//         Optional<DynamicPriceRecord> previous = priceRepository.findFirstByEventOrderByComputedAtDesc(event);

//         previous.ifPresent(p -> {
//             if (!p.getComputedPrice().equals(newPrice)) {
//                 PriceAdjustmentLog log = new PriceAdjustmentLog(
//                         null, event, p.getComputedPrice(), newPrice, "Price updated");
//                 logRepository.save(log);
//             }
//         });

//         return priceRepository.save(record);
//     }

//     @Override
//     public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
//         EventRecord event = eventRepository.findById(eventId)
//                 .orElseThrow(() -> new BadRequestException("Event not found"));
//         return priceRepository.findByEventOrderByComputedAtDesc(event);
//     }

//     @Override
//     public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
//         EventRecord event = eventRepository.findById(eventId)
//                 .orElseThrow(() -> new BadRequestException("Event not found"));
//         return priceRepository.findFirstByEventOrderByComputedAtDesc(event);
//     }

//     @Override
//     public List<DynamicPriceRecord> getAllComputedPrices() {
//         return priceRepository.findAll();
//     }
// }






// //     @Override
// //     public DynamicPriceRecord computeDynamicPrice(Long eventId) {

// //         EventRecord event = eventRepository.findById(eventId).orElse(null);
// //         if (event == null) {
// //             throw new BadRequestException("Event not found");
// //         }

// //         if (!event.getActive()) {
// //             throw new BadRequestException("Event is not active");
// //         }

// //         SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
// //                 .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

// //         List<PricingRule> rules = ruleRepository.findByActiveTrue();
// //         double multiplier = 1.0;
// //         String appliedRules = "";

// //         long daysToEvent = ChronoUnit.DAYS.between(
// //                 java.time.LocalDate.now(), event.getEventDate());

// //         for (PricingRule rule : rules) {
// //             if (inventory.getRemainingSeats() >= rule.getMinRemainingSeats()
// //                     && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats()
// //                     && daysToEvent <= rule.getDaysBeforeEvent()) {

// //                 if (rule.getPriceMultiplier() > multiplier) {
// //                     multiplier = rule.getPriceMultiplier();
// //                     appliedRules = rule.getRuleCode();
// //                 }
// //             }
// //         }

// //         double newPrice = event.getBasePrice() * multiplier;

// //         DynamicPriceRecord record =
// //                 new DynamicPriceRecord(null, eventId, newPrice, appliedRules);

// //         Optional<DynamicPriceRecord> previous =
// //                 priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);

// //         previous.ifPresent(p -> {
// //             if (!p.getComputedPrice().equals(newPrice)) {
// //                 PriceAdjustmentLog log = new PriceAdjustmentLog(
// //                         null, eventId, p.getComputedPrice(), newPrice, "Price updated");
// //                 logRepository.save(log);
// //             }
// //         });

// //         return priceRepository.save(record);
// //     }

// //     @Override
// //     public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
// //         return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
// //     }

// //     @Override
// //     public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
// //         return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
// //     }

// //     @Override
// //     public List<DynamicPriceRecord> getAllComputedPrices() {
// //         return priceRepository.findAll();
// //     }
// // }

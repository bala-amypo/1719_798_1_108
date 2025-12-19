package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepository;
    private final SeatInventoryRecordRepository inventoryRepository;
    private final PricingRuleRepository ruleRepository;
    private final DynamicPriceRecordRepository priceRepository;
    private final PriceAdjustmentLogRepository logRepository;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepository,
            SeatInventoryRecordRepository inventoryRepository,
            PricingRuleRepository ruleRepository,
            DynamicPriceRecordRepository priceRepository,
            PriceAdjustmentLogRepository logRepository) {
        this.eventRepository = eventRepository;
        this.inventoryRepository = inventoryRepository;
        this.ruleRepository = ruleRepository;
        this.priceRepository = priceRepository;
        this.logRepository = logRepository;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BadRequestException("Event not found"));

        if (!Boolean.TRUE.equals(event.getActive())) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEvent_Id(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        List<PricingRule> rules = ruleRepository.findByActiveTrue();
        double multiplier = 1.0;
        String appliedRules = "";

        long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());

        for (PricingRule rule : rules) {
            if (inventory.getRemainingSeats() >= rule.getMinRemainingSeats()
                    && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats()
                    && daysToEvent <= rule.getDaysBeforeEvent()) {

                if (rule.getPriceMultiplier() > multiplier) {
                    multiplier = rule.getPriceMultiplier();
                    appliedRules = rule.getRuleCode();
                }
            }
        }

        double newPrice = event.getBasePrice() * multiplier;

        DynamicPriceRecord record = new DynamicPriceRecord(null, event, newPrice, appliedRules);

        Optional<DynamicPriceRecord> previous = priceRepository.findFirstByEventOrderByComputedAtDesc(event);

        previous.ifPresent(p -> {
            if (!p.getComputedPrice().equals(newPrice)) {
                PriceAdjustmentLog log = new PriceAdjustmentLog(
                        null, event, p.getComputedPrice(), newPrice, "Price updated");
                logRepository.save(log);
            }
        });

        return priceRepository.save(record);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BadRequestException("Event not found"));
        return priceRepository.findByEventOrderByComputedAtDesc(event);
    }

    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BadRequestException("Event not found"));
        return priceRepository.findFirstByEventOrderByComputedAtDesc(event);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}






//     @Override
//     public DynamicPriceRecord computeDynamicPrice(Long eventId) {

//         EventRecord event = eventRepository.findById(eventId).orElse(null);
//         if (event == null) {
//             throw new BadRequestException("Event not found");
//         }

//         if (!event.getActive()) {
//             throw new BadRequestException("Event is not active");
//         }

//         SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
//                 .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

//         List<PricingRule> rules = ruleRepository.findByActiveTrue();
//         double multiplier = 1.0;
//         String appliedRules = "";

//         long daysToEvent = ChronoUnit.DAYS.between(
//                 java.time.LocalDate.now(), event.getEventDate());

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

//         DynamicPriceRecord record =
//                 new DynamicPriceRecord(null, eventId, newPrice, appliedRules);

//         Optional<DynamicPriceRecord> previous =
//                 priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);

//         previous.ifPresent(p -> {
//             if (!p.getComputedPrice().equals(newPrice)) {
//                 PriceAdjustmentLog log = new PriceAdjustmentLog(
//                         null, eventId, p.getComputedPrice(), newPrice, "Price updated");
//                 logRepository.save(log);
//             }
//         });

//         return priceRepository.save(record);
//     }

//     @Override
//     public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
//         return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
//     }

//     @Override
//     public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
//         return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
//     }

//     @Override
//     public List<DynamicPriceRecord> getAllComputedPrices() {
//         return priceRepository.findAll();
//     }
// }

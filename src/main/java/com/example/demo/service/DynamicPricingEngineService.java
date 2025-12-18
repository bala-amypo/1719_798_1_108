package com.example.demo.service;

import com.example.demo.repository.*;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class DynamicPricingEngineServiceImpl {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository inventoryRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;
    private final PriceAdjustmentLogRepository logRepo;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepo,
            SeatInventoryRecordRepository inventoryRepo,
            PricingRuleRepository ruleRepo,
            DynamicPriceRecordRepository priceRepo,
            PriceAdjustmentLogRepository logRepo) {

        this.eventRepo = eventRepo;
        this.inventoryRepo = inventoryRepo;
        this.ruleRepo = ruleRepo;
        this.priceRepo = priceRepo;
        this.logRepo = logRepo;
    }

    public DynamicPriceRecord computeDynamicPrice(Long eventId) {

        EventRecord event = eventRepo.findById(eventId).orElseThrow();
        if (!event.getActive())
            throw new BadRequestException("Event is not active");

        SeatInventoryRecord inv = inventoryRepo.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        double price = event.getBasePrice();
        String rules = "";

        for (PricingRule r : ruleRepo.findByActiveTrue()) {
            if (inv.getRemainingSeats() >= r.getMinRemainingSeats()
                    && inv.getRemainingSeats() <= r.getMaxRemainingSeats()) {
                price *= r.getPriceMultiplier();
                rules += r.getRuleCode() + ",";
            }
        }

        DynamicPriceRecord rec = new DynamicPriceRecord();
        rec.setEventId(eventId);
        rec.setComputedPrice(price);
        rec.setAppliedRuleCodes(rules);
        priceRepo.save(rec);

        priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId)
                .ifPresent(prev -> {
                    if (!prev.getComputedPrice().equals(price)) {
                        PriceAdjustmentLog log = new PriceAdjustmentLog();
                        log.setEventId(eventId);
                        log.setOldPrice(prev.getComputedPrice());
                        log.setNewPrice(price);
                        log.setReason("Dynamic pricing change");
                        logRepo.save(log);
                    }
                });

        return rec;
    }
}

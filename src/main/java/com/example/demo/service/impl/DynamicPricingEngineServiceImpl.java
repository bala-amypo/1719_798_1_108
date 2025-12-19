package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository inventoryRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;
    private final PriceAdjustmentLogRepository logRepo;

    public DynamicPricingEngineServiceImpl(EventRecordRepository eventRepo,
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

        long days = ChronoUnit.DAYS.between(java.time.LocalDate.now(), event.getEventDate());

        double multiplier = 1.0;
        List<String> applied = new ArrayList<>();

        for (PricingRule r : ruleRepo.findByActiveTrue()) {
            if (inv.getRemainingSeats() >= r.getMinRemainingSeats()
                    && inv.getRemainingSeats() <= r.getMaxRemainingSeats()
                    && days <= r.getDaysBeforeEvent()) {
                if (r.getPriceMultiplier() > multiplier) {
                    multiplier = r.getPriceMultiplier();
                    applied.add(r.getRuleCode());
                }
            }
        }

        double price = event.getBasePrice() * multiplier;
        DynamicPriceRecord record = priceRepo.save(
                new DynamicPriceRecord(eventId, price, String.join(",", applied)));

        priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId)
                .ifPresent(prev -> {
                    if (!prev.getComputedPrice().equals(price)) {
                        logRepo.save(new PriceAdjustmentLog(
                                eventId, prev.getComputedPrice(), price, "Auto adjustment"));
                    }
                });

        return record;
    }

    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepo.findByEventIdOrderByComputedAtDesc(eventId);
    }

    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }

    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepo.findAll();
    }
}

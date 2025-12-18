package com.example.demo.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.exception.BadRequestException;

@Service
public class DynamicPricingEngineServiceImpl {

    private final EventRecordRepository eventRepo;
    private final SeatInventoryRecordRepository invRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;
    private final PriceAdjustmentLogRepository logRepo;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepo,
            SeatInventoryRecordRepository invRepo,
            PricingRuleRepository ruleRepo,
            DynamicPriceRecordRepository priceRepo,
            PriceAdjustmentLogRepository logRepo) {
        this.eventRepo = eventRepo;
        this.invRepo = invRepo;
        this.ruleRepo = ruleRepo;
        this.priceRepo = priceRepo;
        this.logRepo = logRepo;
    }

    public DynamicPriceRecord computeDynamicPrice(Long eventId) {

        EventRecord event = eventRepo.findById(eventId).orElseThrow();
        if (!event.getActive())
            throw new BadRequestException("Event is not active");

        SeatInventoryRecord inv = invRepo.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        double price = event.getBasePrice();
        StringBuilder applied = new StringBuilder();

        long days = ChronoUnit.DAYS.between(java.time.LocalDate.now(), event.getEventDate());

        for (PricingRule rule : ruleRepo.findByActiveTrue()) {
            if (inv.getRemainingSeats() >= rule.getMinRemainingSeats()
                    && inv.getRemainingSeats() <= rule.getMaxRemainingSeats()
                    && days <= rule.getDaysBeforeEvent()) {
                price *= rule.getPriceMultiplier();
                applied.append(rule.getRuleCode()).append(",");
            }
        }

        DynamicPriceRecord latest = priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId);
        if (latest != null && Math.abs(latest.getComputedPrice() - price) > 1) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(latest.getComputedPrice());
            log.setNewPrice(price);
            log.setReason("Dynamic price recalculated");
            logRepo.save(log);
        }

        DynamicPriceRecord rec = new DynamicPriceRecord();
        rec.setEventId(eventId);
        rec.setComputedPrice(price);
        rec.setAppliedRuleCodes(applied.toString());
        return priceRepo.save(rec);
    }

    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepo.findByEventIdOrderByComputedAtDesc(eventId);
    }

    public DynamicPriceRecord getLatestPrice(Long eventId) {
        return priceRepo.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }

    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepo.findAll();
    }
}

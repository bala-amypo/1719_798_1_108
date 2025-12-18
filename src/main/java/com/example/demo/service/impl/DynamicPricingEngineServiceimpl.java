package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepository;
    private final SeatInventoryRecordRepository inventoryRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final DynamicPriceRecordRepository priceRepository;
    private final PriceAdjustmentLogRepository logRepository;

    public DynamicPricingEngineServiceImpl(EventRecordRepository eventRepository,
                                           SeatInventoryRecordRepository inventoryRepository,
                                           PricingRuleRepository pricingRuleRepository,
                                           DynamicPriceRecordRepository priceRepository,
                                           PriceAdjustmentLogRepository logRepository) {
        this.eventRepository = eventRepository;
        this.inventoryRepository = inventoryRepository;
        this.pricingRuleRepository = pricingRuleRepository;
        this.priceRepository = priceRepository;
        this.logRepository = logRepository;
    }

    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepository.findById(eventId).orElse(null);
        if (event == null || !event.getActive()) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        double price = event.getBasePrice();
        LocalDate today = LocalDate.now();
        long daysToEvent = ChronoUnit.DAYS.between(today, event.getEventDate());

        List<PricingRule> rules = pricingRuleRepository.findByActiveTrue().stream()
                .filter(r ->
                        inventory.getRemainingSeats() >= r.getMinRemainingSeats() &&
                        inventory.getRemainingSeats() <= r.getMaxRemainingSeats() &&
                        daysToEvent <= r.getDaysBeforeEvent())
                .collect(Collectors.toList());

        String appliedCodes = rules.stream().map(PricingRule::getRuleCode).collect(Collectors.joining(","));

        for (PricingRule rule : rules) {
            price *= rule.getPriceMultiplier();
        }

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(price);
        record.setAppliedRuleCodes(appliedCodes);
        DynamicPriceRecord saved = priceRepository.save(record);

        priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId).ifPresent(prev -> {
            if (!prev.getComputedPrice().equals(price)) {
                PriceAdjustmentLog log = new PriceAdjustmentLog();
                log.setEventId(eventId);
                log.setOldPrice(prev.getComputedPrice());
                log.setNewPrice(price);
                log.setReason("Dynamic pricing adjustment");
                logRepository.save(log);
            }
        });

        return saved;
    }

    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    public DynamicPriceRecord getLatestPrice(Long eventId) {
        return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId).orElse(null);
    }

    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}

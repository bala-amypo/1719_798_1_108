package com.example.demo.service.impl;

import com.example.demo.entity.DynamicPriceRecord;
import com.example.demo.entity.EventRecord;
import com.example.demo.entity.PriceAdjustmentLog;
import com.example.demo.entity.PricingRule;
import com.example.demo.entity.SeatInventoryRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.DynamicPriceRecordRepository;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

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
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {

        EventRecord event = eventRecordRepository.findById(eventId)
                .orElseThrow(() -> new BadRequestException("Event not found"));

        if (!Boolean.TRUE.equals(event.getActive())) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = seatInventoryRecordRepository
                .findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        double computedPrice = event.getBasePrice();

        long daysBeforeEvent =
                ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());

        List<PricingRule> rules = pricingRuleRepository.findByActiveTrue();
        List<String> appliedRuleCodes = new ArrayList<>();

        for (PricingRule rule : rules) {

            boolean seatMatch =
                    inventory.getRemainingSeats() >= rule.getMinRemainingSeats()
                            && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats();

            boolean dateMatch =
                    rule.getDaysBeforeEvent() == null
                            || daysBeforeEvent <= rule.getDaysBeforeEvent();

            if (seatMatch && dateMatch) {
                if (rule.getPriceMultiplier() <= 0) {
                    throw new BadRequestException("Price multiplier must be > 0");
                }
                computedPrice = computedPrice * rule.getPriceMultiplier();
                appliedRuleCodes.add(rule.getRuleCode());
            }
        }

        if (computedPrice <= 0) {
            throw new BadRequestException("Computed price must be > 0");
        }

        Optional<DynamicPriceRecord> previousOpt =
                dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(computedPrice);
        record.setAppliedRuleCodes(String.join(",", appliedRuleCodes));

        dynamicPriceRecordRepository.save(record);

        if (previousOpt.isPresent()) {
            DynamicPriceRecord previous = previousOpt.get();
            if (Double.compare(previous.getComputedPrice(), computedPrice) != 0) {
                PriceAdjustmentLog log = new PriceAdjustmentLog();
                log.setEventId(eventId);
                log.setOldPrice(previous.getComputedPrice());
                log.setNewPrice(computedPrice);
                log.setReason("Dynamic pricing rule adjustment");
                priceAdjustmentLogRepository.save(log);
            }
        }

        return record;
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public DynamicPriceRecord getLatestPrice(Long eventId) {
        return dynamicPriceRecordRepository
                .findFirstByEventIdOrderByComputedAtDesc(eventId)
                .orElse(null);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicPriceRecordRepository.findAll();
    }
}

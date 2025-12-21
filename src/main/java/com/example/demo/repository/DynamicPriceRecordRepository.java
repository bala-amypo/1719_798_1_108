// package com.example.demo.repository;

// import com.example.demo.model.DynamicPriceRecord;
// import com.example.demo.model.EventRecord;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {

//     // Find all price records for an event, ordered by computation date descending
//     List<DynamicPriceRecord> findByEventOrderByComputedAtDesc(EventRecord event);

//     // Find the latest price record for an event
//     Optional<DynamicPriceRecord> findFirstByEventOrderByComputedAtDesc(EventRecord event);
// }

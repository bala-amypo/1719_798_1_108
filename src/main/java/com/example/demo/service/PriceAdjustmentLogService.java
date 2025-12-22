package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;
import java.util.List;

public interface PriceAdjustmentLogService {
    List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);
    List<PriceAdjustmentLog> getAllAdjustments();
    PriceAdjustmentLog logAdjustment(PriceAdjustmentLog adjustment);
}







// package com.example.demo.service;

// import com.example.demo.model.PriceAdjustmentLog;
// import java.util.List;
// import java.util.Optional;

// public interface PriceAdjustmentLogService {
//     PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log);
//     List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);
//     List<PriceAdjustmentLog> getAllAdjustments();
//     //Optional<PriceAdjustmentLog> getAdjustmentById(Long id);
// }












// package com.example.demo.service;






// import com.example.demo.model.PriceAdjustmentLog;

// import java.util.List;

// public interface PriceAdjustmentLogService {

//     PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log);

//     List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);

//     List<PriceAdjustmentLog> getAllAdjustments();
// }

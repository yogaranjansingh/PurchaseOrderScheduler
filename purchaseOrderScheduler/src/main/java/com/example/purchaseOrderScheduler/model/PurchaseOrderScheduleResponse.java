package com.example.purchaseOrderScheduler.model;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Data
public class PurchaseOrderScheduleResponse {

    String message;
    List<Schedule> scheduleList;
    double unUtilizedCapacity;

}

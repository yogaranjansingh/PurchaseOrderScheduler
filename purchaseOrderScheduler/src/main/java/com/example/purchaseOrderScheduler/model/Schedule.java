package com.example.purchaseOrderScheduler.model;

import lombok.Data;

@Data
public class Schedule {

    Slot slot;
    int dockId;
    int purchaseOrderId;
    int itemId;
    int quantity;

}

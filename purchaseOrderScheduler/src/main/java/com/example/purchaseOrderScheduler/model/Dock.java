package com.example.purchaseOrderScheduler.model;

import lombok.Data;

@Data
public class Dock {
    Slot slot;
    int dockId;
    int capacity;
    int usedCapacity;
    int unusedCapacity;


    public Dock(Slot slot, int dockId, int capacity, int usedCapacity, int unusedCapacity) {
        this.slot = slot;
        this.dockId = dockId;
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
        this.unusedCapacity = unusedCapacity;
    }
}

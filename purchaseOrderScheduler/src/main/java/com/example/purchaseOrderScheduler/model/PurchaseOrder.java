package com.example.purchaseOrderScheduler.model;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrder implements Comparable<PurchaseOrder>{

    int id;
    int itemId;
    int quantity;


    @Override
    public int compareTo(PurchaseOrder po) {
        if (po.getQuantity() > this.getQuantity())
            return 1;
        else if (po.getQuantity() < this.getQuantity())
            return -1;
        return 0;
    }
}

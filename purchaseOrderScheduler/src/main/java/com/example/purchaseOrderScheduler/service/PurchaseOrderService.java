package com.example.purchaseOrderScheduler.service;

import com.example.purchaseOrderScheduler.model.PurchaseOrder;
import com.example.purchaseOrderScheduler.model.PurchaseOrderScheduleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderScheduleResponse uploadPurchaseOrderFile(MultipartFile uploadfile) throws IOException;
}

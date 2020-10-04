package com.example.purchaseOrderScheduler.controller;


import com.example.purchaseOrderScheduler.model.Dock;
import com.example.purchaseOrderScheduler.model.PurchaseOrder;
import com.example.purchaseOrderScheduler.model.PurchaseOrderScheduleResponse;
import com.example.purchaseOrderScheduler.service.DockService;
import com.example.purchaseOrderScheduler.service.PurchaseOrderService;
import com.example.purchaseOrderScheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    DockService dockService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    /*
    Health Endpoint
     */
    @GetMapping("/health")
    public ResponseEntity health() {
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    /*
    accepts a dock slots file(.csv)
     */
    @PostMapping("/uploadDockFile")
    public ResponseEntity uploadDockFile(@RequestParam("dockfile") MultipartFile uploadfile) throws IOException {
        List<Dock> docks = dockService.uploadDockFile(uploadfile);
        return new ResponseEntity(docks,HttpStatus.OK);
    }

    /*
    accepts a purchase order file (.csv)
     */
    @PostMapping("/getSchedule")
    public ResponseEntity uploadPurchaseOrderFile(@RequestParam("purchaseOrderFile") MultipartFile uploadfile) throws IOException {
        PurchaseOrderScheduleResponse purchaseOrderScheduleResponse = purchaseOrderService.uploadPurchaseOrderFile(uploadfile);
        return new ResponseEntity(purchaseOrderScheduleResponse,HttpStatus.OK);
    }
}

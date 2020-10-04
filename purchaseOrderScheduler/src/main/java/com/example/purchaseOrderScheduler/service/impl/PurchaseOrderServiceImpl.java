package com.example.purchaseOrderScheduler.service.impl;

import com.example.purchaseOrderScheduler.data.DataProvider;
import com.example.purchaseOrderScheduler.model.Dock;
import com.example.purchaseOrderScheduler.model.PurchaseOrder;
import com.example.purchaseOrderScheduler.model.PurchaseOrderScheduleResponse;
import com.example.purchaseOrderScheduler.model.Schedule;
import com.example.purchaseOrderScheduler.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    DataProvider dataProvider;

    @Override
    public PurchaseOrderScheduleResponse uploadPurchaseOrderFile(MultipartFile uploadfile) throws IOException {

        byte[] bytes = uploadfile.getBytes();
        File purchaseOrderFile = new File("purchaseOrder.txt");
        purchaseOrderFile.createNewFile();

        try {
            OutputStream os = new FileOutputStream(purchaseOrderFile);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            log.error("There was an Exception while reading the file " , e);
        }

        List<PurchaseOrder> purchaseOrderList = readPurchaseOrderFile(purchaseOrderFile);
        dataProvider.addPurchaseOrdersToList(purchaseOrderList);
        PurchaseOrderScheduleResponse purchaseOrderScheduleResponse = getSchedule();
        return purchaseOrderScheduleResponse;
    }

    private List<PurchaseOrder> readPurchaseOrderFile(File purchaseOrderFile) throws IOException {

        String line = null;
        String[] purchases = null;

        int purchaseOrderId = -1;
        int itemId = -1;
        int quantity = -1;

        List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();

        try (BufferedReader br = new BufferedReader(new FileReader(purchaseOrderFile))) {
            br.readLine();

            while ((line = br.readLine()) != null  && line.length()>0) {
                if (line.contains("\""))
                    line = line.replaceAll("\"", "");

                if (line.contains("'"))
                    line = line.replaceAll("'", "");

                log.info("processing line : " + line);

                if (line != null && line.length() > 0)
                    purchases = line.split(",");

                if (purchases.length > 0)
                    purchaseOrderId = Integer.valueOf(purchases[0].trim());

                if (purchases.length > 1)
                    itemId = Integer.valueOf(purchases[1].trim());

                if (purchases.length > 2)
                    quantity = Integer.valueOf(purchases[2].trim());

                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setItemId(itemId);
                purchaseOrder.setQuantity(quantity);
                purchaseOrder.setId(purchaseOrderId);
                purchaseOrderList.add(purchaseOrder);
            }
        }

        //Collections.sort(purchaseOrderList, Collections.reverseOrder());
        return purchaseOrderList;
    }

    PurchaseOrderScheduleResponse getSchedule() {

        PriorityQueue<Dock> capacityPriorityQueue = dataProvider.getCapacityPriorityQueue();
        PriorityQueue<PurchaseOrder> purchaseOrders = dataProvider.getPurchaseOrderPriorityQueue();
        HashMap<PurchaseOrder,Dock> purchaseOrderDockHashMap = new HashMap<PurchaseOrder,Dock>();
        List<Dock> docksList = dataProvider.getDocksList();

        List<Schedule> scheduleList = new ArrayList<Schedule>();
        double unUtilizationCapacity = 0;
        double unUsedCapacity = 0;
        int numberOfDocks = capacityPriorityQueue.size();

        int k=0;
        System.out.println(purchaseOrderDockHashMap);

        while(!purchaseOrders.isEmpty() && !capacityPriorityQueue.isEmpty())
        {
            Schedule schedule = new Schedule();
            Dock dock = capacityPriorityQueue.poll();
            PurchaseOrder po = getPurchaseOrderForDock(purchaseOrders,dock.getUnusedCapacity());
            if(po==null)
            {
                log.error("No suitable purchase order found for dock id : "+dock.getDockId());
                capacityPriorityQueue.remove(dock);
                continue;
            }
            if(dock.getCapacity() >= po.getQuantity())
            {
                log.info("dock id "+dock.getDockId() +" allocated for purchase order id "+po.getId());
                schedule.setSlot(dock.getSlot());
                schedule.setDockId(dock.getDockId());
                schedule.setItemId(po.getItemId());
                schedule.setPurchaseOrderId(po.getId());
                schedule.setQuantity(po.getQuantity());
                dock.setUsedCapacity(dock.getUsedCapacity() + po.getQuantity());
                dock.setUnusedCapacity(dock.getCapacity() - dock.getUsedCapacity());
                capacityPriorityQueue.offer(dock);
                if(dock.getCapacity()==dock.getUsedCapacity())
                    capacityPriorityQueue.remove(dock);
                scheduleList.add(schedule);
                purchaseOrders.remove(po);
                k++;
            }
            purchaseOrderDockHashMap.put(po,dock);
        }

        unUtilizationCapacity = getUnUtilizedCapacity(purchaseOrderDockHashMap, numberOfDocks);
        PurchaseOrderScheduleResponse purchaseOrderScheduleResponse = new PurchaseOrderScheduleResponse();
        purchaseOrderScheduleResponse.setScheduleList(scheduleList);
        purchaseOrderScheduleResponse.setUnUtilizedCapacity(unUtilizationCapacity);
        purchaseOrderScheduleResponse.setMessage("Purchase Order Schedule");
        dataProvider.buildDocksPriorityQueue(docksList);
        return purchaseOrderScheduleResponse;
    }

    /*
    method to go through the map of the purchase orders and dock to get the unutilized capacity factor
     */
    private double getUnUtilizedCapacity(HashMap<PurchaseOrder, Dock> purchaseOrderDockHashMap, int numberOfDocks) {
        double unusedCapacity = 0;
        List<Dock> docks = dataProvider.getDocksList();
        for(Dock dock : docks)
        {
            unusedCapacity = unusedCapacity + Double.valueOf((double)(dock.getCapacity() - (double)dock.getUsedCapacity())/ dock.getCapacity() );
        }
        return Double.valueOf(unusedCapacity/numberOfDocks);
    }

    /*
    this method gets the purchase order thats is equal to or immediate less than the capacity of the dock
     */
    private PurchaseOrder getPurchaseOrderForDock(PriorityQueue<PurchaseOrder> purchaseOrders, int capacity) {
        Iterator<PurchaseOrder> itr = purchaseOrders.iterator();
        while(itr.hasNext())
        {
            PurchaseOrder po = itr.next();
            if(po.getQuantity() > capacity)
            {
                //do nothing
            }
            else
            {
                return po;
            }
        }
        return null;
    }

}


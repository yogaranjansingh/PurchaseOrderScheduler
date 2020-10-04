package com.example.purchaseOrderScheduler.data;

import com.example.purchaseOrderScheduler.model.Dock;
import com.example.purchaseOrderScheduler.model.Purchase;
import com.example.purchaseOrderScheduler.model.PurchaseOrder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@Data
public class DataProvider {

    List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
    List<Dock> docksList = new ArrayList<Dock>();
    PriorityQueue<Dock> capacityPriorityQueue = new PriorityQueue(10, capacityComparator);
    PriorityQueue<PurchaseOrder> purchaseOrderPriorityQueue = new PriorityQueue(10, quantityComparator);
    TreeMap<PurchaseOrder,Dock> purchaseOrderMap = new TreeMap<PurchaseOrder,Dock>();

    public static Comparator<Dock> capacityComparator = new Comparator<Dock>(){
        @Override
        public int compare(Dock d1, Dock d2) {
            if (d1.getUnusedCapacity() > d2.getUnusedCapacity())
                return 1;
            else if (d1.getUnusedCapacity() < d2.getUnusedCapacity())
                return -1;
            return 0;
        }
    };

    public static Comparator<PurchaseOrder> quantityComparator = new Comparator<PurchaseOrder>(){
        @Override
        public int compare(PurchaseOrder d1, PurchaseOrder d2) {
            if (d2.getQuantity() > d1.getQuantity())
                return 1;
            else if (d2.getQuantity() < d1.getQuantity())
                return -1;
            return 0;
        }
    };

    public PurchaseOrder getPurchaseOrderById(int id)
    {
        for(PurchaseOrder po : purchaseOrderList)
        {
            if(po.getId()==id)
                return po;
        }
        return null;
    }

    public Dock getDockById(int id)
    {
        for(Dock dock : docksList)
        {
            if(dock.getDockId()==id)
                return dock;
        }
        return null;
    }

    public void addDocksToDockList(List<Dock> docks)
    {
        for(Dock dock : docks)
            docksList.add(dock);

        buildDocksPriorityQueue(docks);
    }

    public void addPurchaseOrdersToList(List<PurchaseOrder> purchaseOrders)
    {
        for(PurchaseOrder po: purchaseOrders)
            purchaseOrderList.add(po);

        for(PurchaseOrder po: purchaseOrders)
            purchaseOrderPriorityQueue.offer(po);
    }

    public  List<PurchaseOrder> getPurchaseOrderList()
    {
        Collections.sort(purchaseOrderList, quantityComparator);
        return purchaseOrderList;
    }

    public void buildDocksPriorityQueue(List<Dock> docks)
    {
        for(Dock dock : docks)
            capacityPriorityQueue.offer(dock);
    }

}

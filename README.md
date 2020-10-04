# PurchaseOrderScheduler

Java service to create schedule for the purchase orders to offload in the warehouse docks


# API Deftinitions :

    @GetMapping("/health") : Health Endpoint
    

    @PostMapping("/uploadDockFile") :   accepts a dock slots file(.csv), simply uploads the dock file and returns the dock objects created on server
    
    
    @PostMapping("/getSchedule") : accepts a purchase order file(.csv), uploads the purchase orders and creates a schedule for offloading. Returns the optimal schedule for the purchase order to be offloaded 

How to run the application ?
Its a Spring boot application and the main class is PurchaseOrderSchedulerApplication.java This class can be used to run as a spring boot app. or alternatively we can run it from command line. using the below command

mvn spring-boot:run

# Sample request and output :

#####Request -> 
curl http://localhost:8080/health

curl --location --request POST 'http://localhost:8080/uploadDockFile' \
--form 'dockfile=@/Users/ysingh1/Downloads/purchaseOrderScheduler/slotdata1.csv'

Sample response for /purchaseOrderScheduler

```
[
    {
        "slot": {
            "start": "2018-07-31T18:30:00.000+00:00",
            "end": "2018-07-31T19:30:00.000+00:00"
        },
        "dockId": 1,
        "capacity": 10,
        "usedCapacity": 0,
        "unusedCapacity": 10
    }
]
```

curl --location --request POST 'http://localhost:8080/getSchedule' \
--form 'purchaseOrderFile=@/Users/ysingh1/Downloads/purchaseOrderScheduler/po1.csv'


Sample response for /getSchedule

```
 {
    "message": "Purchase Order Schedule",
    "scheduleList": [
        {
            "slot": {
                "start": "2018-07-31T18:30:00.000+00:00",
                "end": "2018-07-31T19:30:00.000+00:00"
            },
            "dockId": 1,
            "purchaseOrderId": 1,
            "itemId": 1,
            "quantity": 9
        }
    ],
    "unUtilizedCapacity": 0.1
}

```

postman collection : https://www.getpostman.com/collections/053622d358401637d94b
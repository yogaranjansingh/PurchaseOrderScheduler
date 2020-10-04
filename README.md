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
    },
    {
        "slot": {
            "start": "2018-07-31T18:30:00.000+00:00",
            "end": "2018-07-31T19:30:00.000+00:00"
        },
        "dockId": 2,
        "capacity": 9,
        "usedCapacity": 0,
        "unusedCapacity": 9
    },
    {
        "slot": {
            "start": "2018-07-31T18:30:00.000+00:00",
            "end": "2018-07-31T19:30:00.000+00:00"
        },
        "dockId": 3,
        "capacity": 5,
        "usedCapacity": 0,
        "unusedCapacity": 5
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

```{
    "message": "Purchase Order Schedule",
    "scheduleList": [
        {
            "slot": {
                "start": "2018-07-31T18:30:00.000+00:00",
                "end": "2018-07-31T19:30:00.000+00:00"
            },
            "dockId": 3,
            "purchaseOrderId": 1,
            "itemId": 1,
            "quantity": 5
        },
        {
            "slot": {
                "start": "2018-07-31T18:30:00.000+00:00",
                "end": "2018-07-31T19:30:00.000+00:00"
            },
            "dockId": 2,
            "purchaseOrderId": 2,
            "itemId": 2,
            "quantity": 7
        },
        {
            "slot": {
                "start": "2018-07-31T18:30:00.000+00:00",
                "end": "2018-07-31T19:30:00.000+00:00"
            },
            "dockId": 2,
            "purchaseOrderId": 3,
            "itemId": 3,
            "quantity": 2
        },
        {
            "slot": {
                "start": "2018-07-31T18:30:00.000+00:00",
                "end": "2018-07-31T19:30:00.000+00:00"
            },
            "dockId": 1,
            "purchaseOrderId": 4,
            "itemId": 4,
            "quantity": 5
        }
    ],
    "unUtilizedCapacity": 0.16666666666666666
}
```

postman collection : https://www.getpostman.com/collections/053622d358401637d94b
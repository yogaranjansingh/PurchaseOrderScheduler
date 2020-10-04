package com.example.purchaseOrderScheduler.service.impl;

import com.example.purchaseOrderScheduler.data.DataProvider;
import com.example.purchaseOrderScheduler.model.Dock;
import com.example.purchaseOrderScheduler.model.Slot;
import com.example.purchaseOrderScheduler.service.DockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DockServiceImpl implements DockService {

    @Autowired
    DataProvider dataProvider;

    @Override
    public List<Dock> uploadDockFile(MultipartFile uploadfile) throws IOException {

        byte[] bytes = uploadfile.getBytes();
        File dockFile = new File("docksFile.txt");
        dockFile.createNewFile();

        try {
            OutputStream os = new FileOutputStream(dockFile);
            os.write(bytes);
            os.close();
        }
        catch (Exception e) {
            log.error("There was an Exception while reading the file " , e);
        }

        List<Dock> docks = readDockFile(dockFile);
        dataProvider.addDocksToDockList(docks);
        return docks;
    }

    private List<Dock> readDockFile(File dockFile) throws IOException {

        String line = null;
        String[] docks = null;
        int dockId = -1;
        String slotStartDate = null;
        String slotEndDate = null;
        int capacity = -1;
        List<Dock> docksList = new ArrayList<Dock>();

        try (BufferedReader br = new BufferedReader(new FileReader(dockFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                if(line.contains("\""))
                    line = line.replaceAll("\"","");

                if(line.contains("'"))
                    line = line.replaceAll("'","");

                log.info("processing line : "+line );

                if (line != null && line.length() > 0)
                    docks = line.split(",");

                if (docks.length > 0)
                    dockId = Integer.valueOf(docks[0].trim());

                if (docks.length > 1)
                    slotStartDate = docks[1].trim();

                if (docks.length > 2)
                    slotEndDate = docks[2].trim();

                if (docks.length > 3)
                    capacity = Integer.valueOf(docks[3].trim());

                Slot slot = new Slot(slotStartDate, slotEndDate);
                Dock dock = new Dock(slot, dockId, capacity, 0, capacity);
                docksList.add(dock);
            }

        }
        return docksList;
    }
}

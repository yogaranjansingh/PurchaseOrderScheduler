package com.example.purchaseOrderScheduler.service;
import com.example.purchaseOrderScheduler.model.Dock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DockService {

    List<Dock> uploadDockFile(MultipartFile uploadfile) throws IOException;

}

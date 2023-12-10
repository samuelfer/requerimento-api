package com.marhashoft.requerimentoapi.storage;

import com.marhashoft.requerimentoapi.util.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arquivos")
public class FileDownloadController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {

        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename\"" + resource.getFilename() +"\"";

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
    }
}

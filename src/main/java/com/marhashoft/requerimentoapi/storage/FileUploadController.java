package com.marhashoft.requerimentoapi.storage;

import com.marhashoft.requerimentoapi.enums.TipoArquivoEnum;
import com.marhashoft.requerimentoapi.service.ArquivoService;
import com.marhashoft.requerimentoapi.util.ArquivoUtil;
import com.marhashoft.requerimentoapi.util.FileStorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/arquivos")
public class FileUploadController {

    @Autowired
    ArquivoService arquivoService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile multipartFile,
                                                     @RequestParam("tipoArquivo") String tipoArquivo)
            throws IOException {
        long size = multipartFile.getSize();

        String fileName = fileStorageService.storeFile(multipartFile);

        arquivoService.salvar(fileName, multipartFile, tipoArquivo);

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUrl(String.valueOf(resource.getURI()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}




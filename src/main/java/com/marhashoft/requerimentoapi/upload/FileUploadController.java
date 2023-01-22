package com.marhashoft.requerimentoapi.upload;

import com.marhashoft.requerimentoapi.service.ArquivoService;
import com.marhashoft.requerimentoapi.util.ArquivoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/arquivos")
public class FileUploadController {

    @Autowired
    ArquivoService arquivoService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileCodeNome = RandomStringUtils.randomAlphanumeric(8);
        long size = multipartFile.getSize();
        String novoNome = ArquivoUtil.transformeNomeArquivo(fileCodeNome + "-" + fileName);

        arquivoService.salvar(novoNome, multipartFile);

        String fileCode = FileUploadUtil.saveFile(novoNome, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(novoNome);
        response.setSize(size);
        response.setDownloadUrl("/downloadFile/" + novoNome);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}




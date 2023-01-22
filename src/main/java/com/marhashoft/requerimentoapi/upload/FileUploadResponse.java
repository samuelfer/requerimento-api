package com.marhashoft.requerimentoapi.upload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {

    private String fileName;
    private String downloadUrl;
    private long size;
}

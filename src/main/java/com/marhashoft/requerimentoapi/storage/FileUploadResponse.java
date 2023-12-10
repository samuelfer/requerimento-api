package com.marhashoft.requerimentoapi.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {

    private String fileName;
    private String downloadUrl;
    private long size;
}

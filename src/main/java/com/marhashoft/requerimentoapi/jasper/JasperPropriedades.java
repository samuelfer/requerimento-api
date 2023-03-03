package com.marhashoft.requerimentoapi.jasper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jasper")
public class JasperPropriedades {

    private String logo;
    private String oficio;
    private String requerimento;
}

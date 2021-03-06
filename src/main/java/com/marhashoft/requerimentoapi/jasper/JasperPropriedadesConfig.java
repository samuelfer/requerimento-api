package com.marhashoft.requerimentoapi.jasper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jasper")
public class JasperPropriedadesConfig {

    private String requerimento;
}

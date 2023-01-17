package com.marhashoft.requerimentoapi.config;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contato() {
        return new Contact(
                "marhasoft",
                "http://www.marhasoft.com.br",
                "marhasoft@springboot.com.br");
    }
    private ApiInfoBuilder informacoesApi() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("Title - Rest API");
        apiInfoBuilder.description("API exemplo de uso de Springboot REST API");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.termsOfServiceUrl("Termo de uso: Open Source");
        apiInfoBuilder.license("Licença - MarhaSoft");
        apiInfoBuilder.licenseUrl("http://www.marhasoft.com.br");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;

    }
    @Bean
    public Docket detalheApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.marhashoft.requerimentoapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.informacoesApi().build())
                .consumes(new HashSet<>(Arrays.asList("application/json")))
                .produces(new HashSet<>(Arrays.asList("application/json")));

        return docket;
    }
}

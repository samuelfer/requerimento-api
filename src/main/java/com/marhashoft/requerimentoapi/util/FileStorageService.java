package com.marhashoft.requerimentoapi.util;

import com.marhashoft.requerimentoapi.storage.FileStorageProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Não foi possível criar o diretório em que os arquivos enviados serão armazenados.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // padroniza o nome do arquivo
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileCodeNome = RandomStringUtils.randomAlphanumeric(8);
        long size = file.getSize();
        String novoNome = ArquivoUtil.transformeNomeArquivo(fileCodeNome + "-" + fileName);

        try {
            // Copia o arquivo para o local de destino (Substituindo arquivo existente pelo
            // mesmo nome)
            Path targetLocation = this.fileStorageLocation.resolve(novoNome);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return novoNome;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Não foi possivel armazenar o arquivo " + fileName + ". Por favor tente novamente!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Arquivo não encontrado " + fileName, ex);
        }
    }
}
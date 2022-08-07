package com.marhashoft.requerimentoapi.exception;

import com.marhashoft.requerimentoapi.model.error.ErrorDetalhe;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalhe> managementResourceNotFoundException(ResourceNotFoundException exception,
                                                                            WebRequest webRequest) {

        ErrorDetalhe errorDetalhe = new ErrorDetalhe(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalhe, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequerimentoApiException.class)
    public ResponseEntity<ErrorDetalhe> managementRequerimentoException(ResourceNotFoundException exception,
                                                                   WebRequest webRequest) {

        ErrorDetalhe errorDetalhe = new ErrorDetalhe(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalhe, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalhe> managementGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorDetalhe errorDetails = new ErrorDetalhe(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomeCampo = ((FieldError)error).getField();
            String mensagem = error.getDefaultMessage();

            errors.put(nomeCampo, mensagem);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrationViolationApiException.class)
    public ResponseEntity<ErrorDetalhe> managementDataInTegrationViolateException(DataIntegrationViolationApiException exception,
                                                                            WebRequest webRequest) {
        ErrorDetalhe errorDetalhe = new ErrorDetalhe(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity(errorDetalhe.getMensagem(), HttpStatus.BAD_REQUEST);
    }
}

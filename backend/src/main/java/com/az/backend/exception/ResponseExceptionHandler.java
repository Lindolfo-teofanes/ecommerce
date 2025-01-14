package com.az.backend.exception;

import com.az.backend.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({ComprasException.class})
    public final ResponseEntity<ErrorResponse> handleQuanticoException(ComprasException ex) {
        ErrorResponse errorResponse = ex.toErrorResponse();
        return ResponseEntity.status(errorResponse.getHttpStatus().value()).body(errorResponse);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex, WebRequest request) {
        String detalhe = ((ServletWebRequest) request).getRequest().getRequestURI();
        return criaErrorResponse(ex.getMessage(), null, detalhe);
    }
    private ResponseEntity<ErrorResponse> criaErrorResponse(String message, HttpStatus status, String detalhe) {
        ErrorResponse.ErrorResponseBuilder builder = ErrorResponse.builder();
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        builder.codigo(String.valueOf(status.value()));
        builder.detalhe(detalhe);
        builder.erro(message);
        return new ResponseEntity<>(builder.build(), status);
    }

}

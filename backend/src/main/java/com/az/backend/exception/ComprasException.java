package com.az.backend.exception;

import com.az.backend.error.ComprasError;
import com.az.backend.error.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;

@Getter
public class ComprasException extends RuntimeException{

    private ComprasError error;

    private Object[] params;

    private ErrorResponse propagatedError;

    public ComprasException() {
        super();
        this.error = ComprasError.CP9999;
    }

    public ComprasException(String message) {
        super(message);
        this.error = ComprasError.CP9999;
    }

    public ComprasException(String message, int statusCode){
        super(message);
        this.error = ComprasError.findByStatusCode(statusCode);
    }

    public ComprasException(Throwable cause) {
        super(cause);
        this.error = ComprasError.CP9999;
    }

    public ComprasException(String message, Throwable cause) {
        super(message, cause);
        this.error = ComprasError.CP9999;
    }

    public ComprasException(ErrorResponse propagatedError) {
        super();
        this.propagatedError = propagatedError;
    }

    public ComprasException(String message, ErrorResponse propagatedError) {
        super(message);
        this.propagatedError = propagatedError;
    }

    public ComprasException(Throwable cause, ErrorResponse propagatedError) {
        super(cause);
        this.propagatedError = propagatedError;
    }

    public ComprasException(String message, Throwable cause, ErrorResponse propagatedError) {
        super(message, cause);
        this.propagatedError = propagatedError;
    }

    public ComprasException(ComprasError error, Object... params) {
        super();
        this.error = error;
        this.params = params;
    }

    public ComprasException(String message, ComprasError error, Object... params) {
        super(message);
        this.error = error;
        this.params = params;
    }

    public ComprasException(Throwable cause, ComprasError error, Object... params) {
        super(cause);
        this.error = error;
        this.params = params;
    }

    public ComprasException(String message, Throwable cause, ComprasError error, Object... params) {
        super(message, cause);
        this.error = error;
        this.params = params;
    }

    public Boolean isPropagatedError() {
        return this.propagatedError != null;
    }

    public String formatedDescription() {
        return (this.isPropagatedError() ? this.propagatedError.getErro() : String.format(this.error.getDescription(), this.getParams()));
    }

    public String getCode() {

        if (this.error != null){
            return this.error.getCode();
        }

        if (this.propagatedError != null){
            return this.propagatedError.getCodigo();
        }
        return ComprasError.CP9999.getCode();
    }

    public String getDetalhe() {
        String parametros = getParametros();

        if (this.getMessage() != null){
            return this.getMessage() + parametros;
        }

        if (this.getCause() != null){
            return this.getCause().getMessage() + parametros;
        }

        if (this.propagatedError != null){
            return this.propagatedError.getDetalhe() + parametros;
        }

        if (StringUtils.isNotBlank(parametros)){
            return parametros;
        }

        return null;
    }

    public String getErro() {

        if (this.error != null){
            return this.error.getDescription();
        }

        if (this.propagatedError != null){
            return this.propagatedError.getErro();
        }

        return null;
    }

    public String getParametros() {
        if (this.params != null && this.params.length > 0){
            StringBuilder sb = new StringBuilder(" Parametros: [ ");
            for (int i = 0; i < this.params.length; i++){
                sb.append(this.params[i]);
                sb.append(" ");
            }
            sb.append("]");
            return sb.toString();
        }
        return StringUtils.EMPTY;
    }

    public HttpStatus getHttpStatus(){
        if (this.error != null){
            return this.error.getHttpStatusCode();
        }
        if (this.propagatedError != null){
            return this.propagatedError.getHttpStatus();
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ErrorResponse toErrorResponse(){
        return ErrorResponse.builder()
                .codigo(this.getCode())
                .httpStatus(this.getHttpStatus())
                .erro(this.getErro())
                .detalhe(this.getDetalhe())
                .build();
    }
}

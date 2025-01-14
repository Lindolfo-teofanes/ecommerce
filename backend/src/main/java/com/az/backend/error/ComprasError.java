package com.az.backend.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ComprasError {

    CP9999("Ocorreu um erro não mapeado", HttpStatus.INTERNAL_SERVER_ERROR),
    CP0001("CEP não encontrado", HttpStatus.NOT_FOUND),
    CP0002("Erro ao criar o pedido", HttpStatus.BAD_REQUEST),
    CP0003("Erro na busca de todos os pedidos", HttpStatus.INTERNAL_SERVER_ERROR),
    CP0004("Não foi possivel atualizar o status", HttpStatus.INTERNAL_SERVER_ERROR),
    CP0005("Status inválido", HttpStatus.BAD_REQUEST),
    CP0006("Erro ao localizar pedido", HttpStatus.NOT_FOUND),
    CP0007("Erro ao enviar mensagem ao RabbitMQ", HttpStatus.SERVICE_UNAVAILABLE),
    CP0008("Erro ao buscar os produtos", HttpStatus.BAD_REQUEST);

    private String description;

    private HttpStatus httpStatusCode;

    ComprasError(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatusCode = httpStatus;
    }

    public String getCode() {
        return this.name();
    }

    public static ComprasError findByStatusCode(int httpStatusCode){
        for (ComprasError error : values()){
            if (error.getHttpStatusCode() != null && error.getHttpStatusCode().value() == httpStatusCode){
                return error;
            }
        }

        return ComprasError.CP9999;
    }
}

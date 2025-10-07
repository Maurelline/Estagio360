package br.com.uniesp.estagio360.domain.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ErrorMessageResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
}

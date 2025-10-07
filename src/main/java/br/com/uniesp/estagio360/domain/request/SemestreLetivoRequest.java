package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SemestreLetivoRequest {
    private String rotulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}

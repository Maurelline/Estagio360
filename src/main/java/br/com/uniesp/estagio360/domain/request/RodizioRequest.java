package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RodizioRequest {
    private Long localEspecialidadeId;
    private Long semestreLetivoId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer vagas;
}
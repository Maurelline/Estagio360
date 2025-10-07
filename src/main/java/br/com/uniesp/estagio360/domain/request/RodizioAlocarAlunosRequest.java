package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class RodizioAlocarAlunosRequest {
    private Long rodizioId;
    private List<Long> alunosIds;
}
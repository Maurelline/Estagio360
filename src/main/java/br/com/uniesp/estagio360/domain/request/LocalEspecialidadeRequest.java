package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

@Data
public class LocalEspecialidadeRequest {
    private Long localParceiroId;
    private Long especialidadeId;
    private Integer quantidadeVagas;
}
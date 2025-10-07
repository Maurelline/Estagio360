package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

@Data
public class CheckinRequest {
    private Long rodizioAlunoId;
    private Double latitude;
    private Double longitude;
}

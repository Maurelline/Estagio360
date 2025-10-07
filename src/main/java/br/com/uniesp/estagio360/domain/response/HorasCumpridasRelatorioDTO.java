package br.com.uniesp.estagio360.domain.response;

import lombok.Data;

@Data
public class HorasCumpridasRelatorioDTO {
    private Long alunoId;
    private Long especialidadeId;
    private Long horasCumpridas;
    private Long horasObrigatorias;
    private boolean cumpriuObrigatorio;
}

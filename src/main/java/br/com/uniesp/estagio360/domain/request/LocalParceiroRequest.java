package br.com.uniesp.estagio360.domain.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocalParceiroRequest {
    private String nome;
    private String sigla;
    private String endereco;
    private String cep;
    private String cidade;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
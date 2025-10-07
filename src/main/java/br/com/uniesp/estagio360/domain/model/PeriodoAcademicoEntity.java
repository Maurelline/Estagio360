package br.com.uniesp.estagio360.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PeriodoAcademicoEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String rotulo;
}

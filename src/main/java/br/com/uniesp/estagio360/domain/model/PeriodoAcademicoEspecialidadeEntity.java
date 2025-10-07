package br.com.uniesp.estagio360.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PeriodoAcademicoEspecialidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cargaHoraria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "periodo_academico_id", nullable = false)
    private PeriodoAcademicoEntity periodoAcademico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "especialidade_id", nullable = false)
    private EspecialidadeEntity especialidade;
}

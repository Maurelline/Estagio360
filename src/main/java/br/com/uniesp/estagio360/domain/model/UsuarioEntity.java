package br.com.uniesp.estagio360.domain.model;

import br.com.uniesp.estagio360.domain.enums.TipoUsuarioEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuarioEnum perfil;

    @ManyToOne
    @JoinColumn(name = "periodo_academico_id")
    private PeriodoAcademicoEntity periodoAcademico;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private EspecialidadeEntity especialidadePrincipal;
}


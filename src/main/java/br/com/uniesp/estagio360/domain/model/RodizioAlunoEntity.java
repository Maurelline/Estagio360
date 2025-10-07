package br.com.uniesp.estagio360.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RodizioAlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rodizio_id", nullable = false)
    private RodizioEntity rodizio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private UsuarioEntity aluno;
}
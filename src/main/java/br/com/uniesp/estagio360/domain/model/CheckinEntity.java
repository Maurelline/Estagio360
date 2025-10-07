package br.com.uniesp.estagio360.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CheckinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rodizio_aluno_id", nullable = false)
    private RodizioAlunoEntity rodizioAluno;

    @Column(nullable = false)
    private LocalDateTime dataHoraCheckin;

    private LocalDateTime dataHoraCheckout;

    @Column
    private Double latitude;

    @Column
    private Double longitude;
}
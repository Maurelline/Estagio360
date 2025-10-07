package br.com.uniesp.estagio360.domain.ports;

import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodoAcademicoEspecialidadeRepository extends JpaRepository<PeriodoAcademicoEspecialidadeEntity, Long> {
    Optional<PeriodoAcademicoEspecialidadeEntity> findByPeriodoAcademico_RotuloAndEspecialidade_Id(String periodoAcademicoRotulo, Long especialidadeId);
}

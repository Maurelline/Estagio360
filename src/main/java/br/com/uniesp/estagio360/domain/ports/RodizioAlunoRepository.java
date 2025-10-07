package br.com.uniesp.estagio360.domain.ports;

import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RodizioAlunoRepository extends JpaRepository<RodizioAlunoEntity, Long> {
    long countByRodizioId(Long rodizioId);

    List<RodizioAlunoEntity> findByRodizioId(Long rodizioId);

    boolean existsByRodizioIdAndAlunoId(Long rodizioId, Long alunoId);

    List<RodizioAlunoEntity> findByAlunoId(Long alunoId);
}
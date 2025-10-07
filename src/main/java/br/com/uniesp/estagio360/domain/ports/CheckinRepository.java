package br.com.uniesp.estagio360.domain.ports;

import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<CheckinEntity, Long> {
    List<CheckinEntity> findAllByRodizioAlunoIdIn(List<Long> rodizioAlunoIds);
}
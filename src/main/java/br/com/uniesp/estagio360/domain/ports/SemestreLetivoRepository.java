package br.com.uniesp.estagio360.domain.ports;

import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestreLetivoRepository extends JpaRepository<SemestreLetivoEntity, Long> {
}

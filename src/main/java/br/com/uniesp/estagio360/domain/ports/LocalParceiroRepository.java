package br.com.uniesp.estagio360.domain.ports;

import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalParceiroRepository extends JpaRepository<LocalParceiroEntity, Long> {
}

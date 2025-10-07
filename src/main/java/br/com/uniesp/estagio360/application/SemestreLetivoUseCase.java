package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.ports.SemestreLetivoRepository;
import br.com.uniesp.estagio360.domain.request.SemestreLetivoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemestreLetivoUseCase {

    private final SemestreLetivoRepository semestreLetivoRepository;

    public Optional<SemestreLetivoEntity> buscarSemestrePorID(Long id) {
        return semestreLetivoRepository.findById(id);
    }

    public SemestreLetivoEntity criarSemestreLetivo(SemestreLetivoRequest request) {
        var entity = new SemestreLetivoEntity();
        entity.setRotulo(request.getRotulo());
        entity.setDataInicio(request.getDataInicio());
        entity.setDataFim(request.getDataFim());

        return semestreLetivoRepository.save(entity);
    }

    public SemestreLetivoEntity editarSemestreLetivo(Long id, SemestreLetivoRequest request) {
        var entityOpt = semestreLetivoRepository.findById(id);
        if (entityOpt.isPresent()) {
            var entity = entityOpt.get();
            if (request.getRotulo() != null) {
                entity.setRotulo(request.getRotulo());
            }
            if (request.getDataInicio() != null) {
                entity.setDataInicio(request.getDataInicio());
            }
            if (request.getDataFim() != null) {
                entity.setDataFim(request.getDataFim());
            }
            return semestreLetivoRepository.save(entity);
        }
        throw new NotFoundException("Semestre", id.toString());
    }

    public void removerSemestreLetivo(Long id) {
        semestreLetivoRepository.deleteById(id);
    }

    public List<SemestreLetivoEntity> listarSemestreLetivo() {
        return semestreLetivoRepository.findAll();
    }
}

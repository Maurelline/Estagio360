package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.ports.LocalEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioRepository;
import br.com.uniesp.estagio360.domain.ports.SemestreLetivoRepository;
import br.com.uniesp.estagio360.domain.request.RodizioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodizioUseCase {
    private final RodizioRepository rodizioRepository;
    private final LocalEspecialidadeRepository localEspecialidadeRepository;
    private final SemestreLetivoRepository semestreLetivoRepository;

    public RodizioEntity criar(RodizioRequest request) {
        LocalEspecialidadeEntity localEspecialidade = localEspecialidadeRepository.findById(request.getLocalEspecialidadeId())
                .orElseThrow(() -> new NotFoundException("LocalEspecialidade", request.getLocalEspecialidadeId().toString()));
        SemestreLetivoEntity semestreLetivo = semestreLetivoRepository.findById(request.getSemestreLetivoId())
                .orElseThrow(() -> new NotFoundException("SemestreLetivo", request.getSemestreLetivoId().toString()));

        RodizioEntity entity = new RodizioEntity();
        entity.setLocalEspecialidade(localEspecialidade);
        entity.setSemestreLetivo(semestreLetivo);
        entity.setData(request.getData());
        entity.setHoraInicio(request.getHoraInicio());
        entity.setHoraFim(request.getHoraFim());
        entity.setVagas(request.getVagas());

        return rodizioRepository.save(entity);
    }

    public List<RodizioEntity> listar() {
        return rodizioRepository.findAll();
    }
}
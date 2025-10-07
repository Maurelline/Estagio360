package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.LocalEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.LocalParceiroRepository;
import br.com.uniesp.estagio360.domain.request.LocalEspecialidadeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalEspecialidadeUseCase {
    private final LocalEspecialidadeRepository localEspecialidadeRepository;
    private final LocalParceiroRepository localParceiroRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public LocalEspecialidadeEntity criar(LocalEspecialidadeRequest request) {
        LocalParceiroEntity local = localParceiroRepository.findById(request.getLocalParceiroId())
                .orElseThrow(() -> new NotFoundException("LocalParceiro", request.getLocalParceiroId().toString()));
        EspecialidadeEntity especialidade = especialidadeRepository.findById(request.getEspecialidadeId())
                .orElseThrow(() -> new NotFoundException("Especialidade", request.getEspecialidadeId().toString()));

        LocalEspecialidadeEntity entity = new LocalEspecialidadeEntity();
        entity.setLocalParceiro(local);
        entity.setEspecialidade(especialidade);
        entity.setQuantidadeVagas(request.getQuantidadeVagas());

        return localEspecialidadeRepository.save(entity);
    }

    public List<LocalEspecialidadeEntity> listar() {
        return localEspecialidadeRepository.findAll();
    }
}
package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.request.EspecialidadeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspecialidadeUseCase {

    private final EspecialidadeRepository especialidadeRepository;

    public Optional<EspecialidadeEntity> buscarEspecialidadePorID(Long id) {
        return especialidadeRepository.findById(id);
    }

    public EspecialidadeEntity criarEspecialidade(EspecialidadeRequest request) {
        var entity = new EspecialidadeEntity();
        entity.setNome(request.getNome());

        return especialidadeRepository.save(entity);
    }

    public EspecialidadeEntity editarEspecialidade(Long id, EspecialidadeRequest request) {
        var entityOpt = especialidadeRepository.findById(id);
        if (entityOpt.isPresent()) {
            var entity = entityOpt.get();
            if (request.getNome() != null) {
                entity.setNome(request.getNome());
            }

            return especialidadeRepository.save(entity);
        }
        throw new NotFoundException("Especialidade", id.toString());
    }

    public void removerEspecialidade(Long id) {
        especialidadeRepository.deleteById(id);
    }

    public List<EspecialidadeEntity> listarEspecialidades() {
        return especialidadeRepository.findAll();
    }
}

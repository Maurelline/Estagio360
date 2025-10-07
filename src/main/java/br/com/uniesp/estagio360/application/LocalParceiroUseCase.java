package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.ports.LocalParceiroRepository;
import br.com.uniesp.estagio360.domain.request.LocalParceiroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalParceiroUseCase {
    private final LocalParceiroRepository localParceiroRepository;

    public LocalParceiroEntity criar(LocalParceiroRequest request) {
        LocalParceiroEntity entity = new LocalParceiroEntity();
        entity.setNome(request.getNome());
        entity.setSigla(request.getSigla());
        entity.setEndereco(request.getEndereco());
        entity.setCep(request.getCep());
        entity.setCidade(request.getCidade());
        entity.setLatitude(request.getLatitude());
        entity.setLongitude(request.getLongitude());
        return localParceiroRepository.save(entity);
    }

    public List<LocalParceiroEntity> listar() {
        return localParceiroRepository.findAll();
    }

    public Optional<LocalParceiroEntity> buscarPorId(Long id) {
        return localParceiroRepository.findById(id);
    }

    public LocalParceiroEntity editar(Long id, LocalParceiroRequest request) {
        LocalParceiroEntity entity = localParceiroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LocalParceiro", id.toString()));

        if (request.getNome() != null) entity.setNome(request.getNome());
        if (request.getSigla() != null) entity.setSigla(request.getSigla());
        if (request.getEndereco() != null) entity.setEndereco(request.getEndereco());
        if (request.getCep() != null) entity.setCep(request.getCep());
        if (request.getCidade() != null) entity.setCidade(request.getCidade());
        if (request.getLatitude() != null) entity.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) entity.setLongitude(request.getLongitude());

        return localParceiroRepository.save(entity);
    }

    public void remover(Long id) {
        if (!localParceiroRepository.existsById(id)) {
            throw new NotFoundException("LocalParceiro", id.toString());
        }
        localParceiroRepository.deleteById(id);
    }
}
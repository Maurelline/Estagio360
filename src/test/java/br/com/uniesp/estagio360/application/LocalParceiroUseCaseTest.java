package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.ports.LocalParceiroRepository;
import br.com.uniesp.estagio360.domain.request.LocalParceiroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalParceiroUseCaseTest {

    @Mock
    private LocalParceiroRepository repository;

    @InjectMocks
    private LocalParceiroUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LocalParceiroRequest buildRequest() {
        LocalParceiroRequest req = new LocalParceiroRequest();
        req.setNome("Nome");
        req.setSigla("SIG");
        req.setEndereco("Endereco");
        req.setCep("12345-678");
        req.setCidade("Cidade");
        req.setLatitude(BigDecimal.valueOf(1.23));
        req.setLongitude(BigDecimal.valueOf(4.56));
        return req;
    }

    private LocalParceiroEntity buildEntity(Long id) {
        LocalParceiroEntity entity = new LocalParceiroEntity();
        entity.setId(id);
        entity.setNome("Nome");
        entity.setSigla("SIG");
        entity.setEndereco("Endereco");
        entity.setCep("12345-678");
        entity.setCidade("Cidade");
        entity.setLatitude(BigDecimal.valueOf(1.23));
        entity.setLongitude(BigDecimal.valueOf(4.56));
        return entity;
    }

    @Test
    void criar_shouldSaveAndReturnEntity() {
        LocalParceiroRequest req = buildRequest();
        LocalParceiroEntity saved = buildEntity(1L);

        when(repository.save(any(LocalParceiroEntity.class))).thenReturn(saved);

        LocalParceiroEntity result = useCase.criar(req);

        assertEquals(saved, result);
        verify(repository).save(any(LocalParceiroEntity.class));
    }

    @Test
    void listar_shouldReturnAllEntities() {
        List<LocalParceiroEntity> list = Arrays.asList(buildEntity(1L), buildEntity(2L));
        when(repository.findAll()).thenReturn(list);

        List<LocalParceiroEntity> result = useCase.listar();

        assertEquals(list, result);
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_shouldReturnEntityIfExists() {
        LocalParceiroEntity entity = buildEntity(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<LocalParceiroEntity> result = useCase.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(repository).findById(1L);
    }

    @Test
    void buscarPorId_shouldReturnEmptyIfNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<LocalParceiroEntity> result = useCase.buscarPorId(1L);

        assertFalse(result.isPresent());
        verify(repository).findById(1L);
    }

    @Test
    void editar_shouldUpdateAndReturnEntity() {
        LocalParceiroEntity entity = buildEntity(1L);
        LocalParceiroRequest req = buildRequest();
        req.setNome("NovoNome");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(LocalParceiroEntity.class))).thenAnswer(i -> i.getArgument(0));

        LocalParceiroEntity result = useCase.editar(1L, req);

        assertEquals("NovoNome", result.getNome());
        verify(repository).findById(1L);
        verify(repository).save(entity);
    }

    @Test
    void editar_shouldThrowIfNotFound() {
        LocalParceiroRequest req = buildRequest();
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.editar(1L, req));
        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void remover_shouldDeleteIfExists() {
        when(repository.existsById(1L)).thenReturn(true);

        useCase.remover(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void remover_shouldThrowIfNotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> useCase.remover(1L));
        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(any());
    }
}
package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.request.EspecialidadeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EspecialidadeUseCaseTest {

    private EspecialidadeRepository repository;
    private EspecialidadeUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(EspecialidadeRepository.class);
        useCase = new EspecialidadeUseCase(repository);
    }

    @Test
    void buscarEspecialidadePorID_found() {
        EspecialidadeEntity entity = new EspecialidadeEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<EspecialidadeEntity> result = useCase.buscarEspecialidadePorID(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(repository).findById(1L);
    }

    @Test
    void buscarEspecialidadePorID_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<EspecialidadeEntity> result = useCase.buscarEspecialidadePorID(2L);

        assertFalse(result.isPresent());
        verify(repository).findById(2L);
    }

    @Test
    void criarEspecialidade_success() {
        EspecialidadeRequest request = new EspecialidadeRequest();
        request.setNome("Cardiologia");

        EspecialidadeEntity savedEntity = new EspecialidadeEntity();
        savedEntity.setNome("Cardiologia");

        when(repository.save(any(EspecialidadeEntity.class))).thenReturn(savedEntity);

        EspecialidadeEntity result = useCase.criarEspecialidade(request);

        assertEquals("Cardiologia", result.getNome());
        ArgumentCaptor<EspecialidadeEntity> captor = ArgumentCaptor.forClass(EspecialidadeEntity.class);
        verify(repository).save(captor.capture());
        assertEquals("Cardiologia", captor.getValue().getNome());
    }

    @Test
    void editarEspecialidade_foundAndUpdate() {
        EspecialidadeRequest request = new EspecialidadeRequest();
        request.setNome("Neurologia");

        EspecialidadeEntity entity = new EspecialidadeEntity();
        entity.setNome("Cardiologia");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(EspecialidadeEntity.class))).thenReturn(entity);

        EspecialidadeEntity result = useCase.editarEspecialidade(1L, request);

        assertEquals("Neurologia", result.getNome());
        verify(repository).findById(1L);
        verify(repository).save(entity);
    }

    @Test
    void editarEspecialidade_notFound() {
        EspecialidadeRequest request = new EspecialidadeRequest();
        request.setNome("Neurologia");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                useCase.editarEspecialidade(1L, request)
        );
    }

    @Test
    void removerEspecialidade_success() {
        doNothing().when(repository).deleteById(1L);

        useCase.removerEspecialidade(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void listarEspecialidades_success() {
        EspecialidadeEntity e1 = new EspecialidadeEntity();
        EspecialidadeEntity e2 = new EspecialidadeEntity();
        when(repository.findAll()).thenReturn(List.of(e1, e2));

        List<EspecialidadeEntity> result = useCase.listarEspecialidades();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }
}
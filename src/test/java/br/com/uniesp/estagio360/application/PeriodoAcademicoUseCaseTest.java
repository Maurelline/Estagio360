package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PeriodoAcademicoUseCaseTest {

    private PeriodoAcademicoRepository repository;
    private PeriodoAcademicoUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(PeriodoAcademicoRepository.class);
        useCase = new PeriodoAcademicoUseCase(repository);
    }

    @Test
    void buscarPeriodoAcademico_shouldReturnEntity_whenFound() {
        var entity = new PeriodoAcademicoEntity();
        entity.setRotulo("P1");
        when(repository.findById("P1")).thenReturn(Optional.of(entity));

        Optional<PeriodoAcademicoEntity> result = useCase.buscarPeriodoAcademico("P1");

        assertTrue(result.isPresent());
        assertEquals("P1", result.get().getRotulo());
        verify(repository).findById("P1");
    }

    @Test
    void buscarPeriodoAcademico_shouldReturnEmpty_whenNotFound() {
        when(repository.findById("P2")).thenReturn(Optional.empty());

        Optional<PeriodoAcademicoEntity> result = useCase.buscarPeriodoAcademico("P2");

        assertFalse(result.isPresent());
        verify(repository).findById("P2");
    }

    @Test
    void listarPeriodos_shouldReturnList() {
        var entity1 = new PeriodoAcademicoEntity();
        var entity2 = new PeriodoAcademicoEntity();
        when(repository.findAll()).thenReturn(List.of(entity1, entity2));

        List<PeriodoAcademicoEntity> result = useCase.listarPeriodos();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void criarPeriodoAcademico_shouldSaveAndReturnEntity() {
        ArgumentCaptor<PeriodoAcademicoEntity> captor = ArgumentCaptor.forClass(PeriodoAcademicoEntity.class);
        var savedEntity = new PeriodoAcademicoEntity();
        savedEntity.setRotulo("P1");
        when(repository.save(any(PeriodoAcademicoEntity.class))).thenReturn(savedEntity);

        PeriodoAcademicoEntity result = useCase.criarPeriodoAcademico("P1");

        verify(repository).save(captor.capture());
        assertEquals("P1", captor.getValue().getRotulo());
        assertEquals("P1", result.getRotulo());
    }

    @Test
    void removerPeriodoAcademico_shouldCallDeleteById() {
        useCase.removerPeriodoAcademico("P2");

        verify(repository).deleteById("P2");
    }
}
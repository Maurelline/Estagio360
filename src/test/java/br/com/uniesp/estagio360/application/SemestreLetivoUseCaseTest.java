package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.ports.SemestreLetivoRepository;
import br.com.uniesp.estagio360.domain.request.SemestreLetivoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SemestreLetivoUseCaseTest {

    private SemestreLetivoRepository repository;
    private SemestreLetivoUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(SemestreLetivoRepository.class);
        useCase = new SemestreLetivoUseCase(repository);
    }

    @Test
    void buscarSemestrePorID_found() {
        SemestreLetivoEntity entity = new SemestreLetivoEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<SemestreLetivoEntity> result = useCase.buscarSemestrePorID(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(repository).findById(1L);
    }

    @Test
    void buscarSemestrePorID_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<SemestreLetivoEntity> result = useCase.buscarSemestrePorID(2L);

        assertFalse(result.isPresent());
        verify(repository).findById(2L);
    }

    @Test
    void criarSemestreLetivo_success() {
        SemestreLetivoRequest request = new SemestreLetivoRequest();
        request.setRotulo("2024/1");
        request.setDataInicio(LocalDate.of(2024, 1, 1));
        request.setDataFim(LocalDate.of(2024, 6, 30));

        SemestreLetivoEntity savedEntity = new SemestreLetivoEntity();
        when(repository.save(any(SemestreLetivoEntity.class))).thenReturn(savedEntity);

        SemestreLetivoEntity result = useCase.criarSemestreLetivo(request);

        assertEquals(savedEntity, result);

        ArgumentCaptor<SemestreLetivoEntity> captor = ArgumentCaptor.forClass(SemestreLetivoEntity.class);
        verify(repository).save(captor.capture());
        SemestreLetivoEntity entity = captor.getValue();
        assertEquals("2024/1", entity.getRotulo());
        assertEquals(LocalDate.of(2024, 1, 1), entity.getDataInicio());
        assertEquals(LocalDate.of(2024, 6, 30), entity.getDataFim());
    }

    @Test
    void editarSemestreLetivo_success() {
        Long id = 1L;
        SemestreLetivoEntity entity = new SemestreLetivoEntity();
        entity.setRotulo("old");
        entity.setDataInicio(LocalDate.of(2023, 1, 1));
        entity.setDataFim(LocalDate.of(2023, 6, 30));

        SemestreLetivoRequest request = new SemestreLetivoRequest();
        request.setRotulo("new");
        request.setDataInicio(LocalDate.of(2024, 1, 1));
        request.setDataFim(LocalDate.of(2024, 6, 30));

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(any(SemestreLetivoEntity.class))).thenReturn(entity);

        SemestreLetivoEntity result = useCase.editarSemestreLetivo(id, request);

        assertEquals("new", result.getRotulo());
        assertEquals(LocalDate.of(2024, 1, 1), result.getDataInicio());
        assertEquals(LocalDate.of(2024, 6, 30), result.getDataFim());
        verify(repository).save(entity);
    }

    @Test
    void editarSemestreLetivo_notFound() {
        Long id = 99L;
        SemestreLetivoRequest request = new SemestreLetivoRequest();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.editarSemestreLetivo(id, request));
    }

    @Test
    void removerSemestreLetivo_success() {
        Long id = 1L;
        useCase.removerSemestreLetivo(id);
        verify(repository).deleteById(id);
    }

    @Test
    void listarSemestreLetivo_success() {
        List<SemestreLetivoEntity> list = List.of(new SemestreLetivoEntity(), new SemestreLetivoEntity());
        when(repository.findAll()).thenReturn(list);

        List<SemestreLetivoEntity> result = useCase.listarSemestreLetivo();

        assertEquals(2, result.size());
        assertEquals(list, result);
        verify(repository).findAll();
    }
}
package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.SemestreLetivoUseCase;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.request.SemestreLetivoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class SemestreLetivoControllerTest {

    @Mock
    private SemestreLetivoUseCase semestreLetivoUseCase;

    @InjectMocks
    private SemestreLetivoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarSemestrePorID_found() {
        SemestreLetivoEntity entity = new SemestreLetivoEntity();
        when(semestreLetivoUseCase.buscarSemestrePorID(1L)).thenReturn(Optional.of(entity));

        ResponseEntity<SemestreLetivoEntity> response = controller.buscarSemestrePorID(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void buscarSemestrePorID_notFound() {
        when(semestreLetivoUseCase.buscarSemestrePorID(1L)).thenReturn(Optional.empty());

        ResponseEntity<SemestreLetivoEntity> response = controller.buscarSemestrePorID(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void listarSemestreLetivo() {
        List<SemestreLetivoEntity> list = List.of(new SemestreLetivoEntity(), new SemestreLetivoEntity());
        when(semestreLetivoUseCase.listarSemestreLetivo()).thenReturn(list);

        ResponseEntity<List<SemestreLetivoEntity>> response = controller.listarSemestreLetivo();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    void criarSemestreLetivo() {
        SemestreLetivoRequest request = new SemestreLetivoRequest();
        SemestreLetivoEntity entity = new SemestreLetivoEntity();
        when(semestreLetivoUseCase.criarSemestreLetivo(request)).thenReturn(entity);

        ResponseEntity<SemestreLetivoEntity> response = controller.criarSemestreLetivo(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void editarSemestreLetivo() {
        SemestreLetivoRequest request = new SemestreLetivoRequest();
        SemestreLetivoEntity entity = new SemestreLetivoEntity();
        when(semestreLetivoUseCase.editarSemestreLetivo(1L, request)).thenReturn(entity);

        ResponseEntity<SemestreLetivoEntity> response = controller.editarSemestreLetivo(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void removerSemestreLetivo() {
        doNothing().when(semestreLetivoUseCase).removerSemestreLetivo(1L);

        ResponseEntity<Void> response = controller.removerSemestreLetivo(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(semestreLetivoUseCase, times(1)).removerSemestreLetivo(1L);
    }
}
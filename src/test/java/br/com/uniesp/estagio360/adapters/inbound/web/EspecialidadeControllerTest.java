package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.EspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.request.EspecialidadeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class EspecialidadeControllerTest {

    @Mock
    private EspecialidadeUseCase especialidadeUseCase;

    @InjectMocks
    private EspecialidadeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarEspecialidadePorID_found() {
        EspecialidadeEntity entity = new EspecialidadeEntity();
        when(especialidadeUseCase.buscarEspecialidadePorID(1L)).thenReturn(Optional.of(entity));

        ResponseEntity<EspecialidadeEntity> response = controller.buscarEspecialidadePorID(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void buscarEspecialidadePorID_notFound() {
        when(especialidadeUseCase.buscarEspecialidadePorID(1L)).thenReturn(Optional.empty());

        ResponseEntity<EspecialidadeEntity> response = controller.buscarEspecialidadePorID(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void listarEspecialidades() {
        EspecialidadeEntity e1 = new EspecialidadeEntity();
        EspecialidadeEntity e2 = new EspecialidadeEntity();
        List<EspecialidadeEntity> list = Arrays.asList(e1, e2);
        when(especialidadeUseCase.listarEspecialidades()).thenReturn(list);

        ResponseEntity<List<EspecialidadeEntity>> response = controller.listarEspecialidades();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    void criarEspecialidade() {
        EspecialidadeRequest request = new EspecialidadeRequest();
        EspecialidadeEntity entity = new EspecialidadeEntity();
        when(especialidadeUseCase.criarEspecialidade(request)).thenReturn(entity);

        ResponseEntity<EspecialidadeEntity> response = controller.criarEspecialidade(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void editarEspecialidade() {
        EspecialidadeRequest request = new EspecialidadeRequest();
        EspecialidadeEntity entity = new EspecialidadeEntity();
        when(especialidadeUseCase.editarEspecialidade(1L, request)).thenReturn(entity);

        ResponseEntity<EspecialidadeEntity> response = controller.editarEspecialidade(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void removerEspecialidade() {
        doNothing().when(especialidadeUseCase).removerEspecialidade(1L);

        ResponseEntity<EspecialidadeEntity> response = controller.removerEspecialidade(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(especialidadeUseCase, times(1)).removerEspecialidade(1L);
    }
}
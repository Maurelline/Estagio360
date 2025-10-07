package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.LocalEspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.request.LocalEspecialidadeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LocalEspecialidadeControllerTest {

    @Mock
    private LocalEspecialidadeUseCase useCase;

    @InjectMocks
    private LocalEspecialidadeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        LocalEspecialidadeRequest request = new LocalEspecialidadeRequest();
        LocalEspecialidadeEntity entity = new LocalEspecialidadeEntity();
        when(useCase.criar(ArgumentMatchers.any(LocalEspecialidadeRequest.class))).thenReturn(entity);

        ResponseEntity<LocalEspecialidadeEntity> response = controller.criar(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(useCase, times(1)).criar(request);
    }

    @Test
    void testListar() {
        LocalEspecialidadeEntity entity1 = new LocalEspecialidadeEntity();
        LocalEspecialidadeEntity entity2 = new LocalEspecialidadeEntity();
        List<LocalEspecialidadeEntity> entities = Arrays.asList(entity1, entity2);
        when(useCase.listar()).thenReturn(entities);

        ResponseEntity<List<LocalEspecialidadeEntity>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entities, response.getBody());
        verify(useCase, times(1)).listar();
    }
}
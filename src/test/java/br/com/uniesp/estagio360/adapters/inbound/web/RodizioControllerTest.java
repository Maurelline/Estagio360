package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.RodizioUseCase;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.request.RodizioRequest;
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

class RodizioControllerTest {

    @Mock
    private RodizioUseCase useCase;

    @InjectMocks
    private RodizioController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_DeveRetornarRodizioCriado() {
        RodizioRequest request = new RodizioRequest();
        RodizioEntity entity = new RodizioEntity();
        when(useCase.criar(ArgumentMatchers.any(RodizioRequest.class))).thenReturn(entity);

        ResponseEntity<RodizioEntity> response = controller.criar(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(useCase, times(1)).criar(request);
    }

    @Test
    void listar_DeveRetornarListaDeRodizios() {
        RodizioEntity entity1 = new RodizioEntity();
        RodizioEntity entity2 = new RodizioEntity();
        List<RodizioEntity> entities = Arrays.asList(entity1, entity2);
        when(useCase.listar()).thenReturn(entities);

        ResponseEntity<List<RodizioEntity>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entities, response.getBody());
        verify(useCase, times(1)).listar();
    }
}
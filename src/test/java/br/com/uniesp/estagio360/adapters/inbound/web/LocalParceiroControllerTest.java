package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.LocalParceiroUseCase;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.request.LocalParceiroRequest;
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

class LocalParceiroControllerTest {

    @Mock
    private LocalParceiroUseCase localParceiroUseCase;

    @InjectMocks
    private LocalParceiroController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveRetornarEntidadeCriada() {
        LocalParceiroRequest request = new LocalParceiroRequest();
        LocalParceiroEntity entity = new LocalParceiroEntity();
        when(localParceiroUseCase.criar(request)).thenReturn(entity);

        ResponseEntity<LocalParceiroEntity> response = controller.criar(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(localParceiroUseCase).criar(request);
    }

    @Test
    void listar_deveRetornarListaDeEntidades() {
        List<LocalParceiroEntity> lista = Arrays.asList(new LocalParceiroEntity(), new LocalParceiroEntity());
        when(localParceiroUseCase.listar()).thenReturn(lista);

        ResponseEntity<List<LocalParceiroEntity>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(lista, response.getBody());
        verify(localParceiroUseCase).listar();
    }

    @Test
    void buscarPorId_quandoEncontrado_deveRetornarEntidade() {
        Long id = 1L;
        LocalParceiroEntity entity = new LocalParceiroEntity();
        when(localParceiroUseCase.buscarPorId(id)).thenReturn(Optional.of(entity));

        ResponseEntity<LocalParceiroEntity> response = controller.buscarPorId(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(localParceiroUseCase).buscarPorId(id);
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveRetornarNotFound() {
        Long id = 1L;
        when(localParceiroUseCase.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<LocalParceiroEntity> response = controller.buscarPorId(id);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(localParceiroUseCase).buscarPorId(id);
    }

    @Test
    void editar_deveRetornarEntidadeEditada() {
        Long id = 1L;
        LocalParceiroRequest request = new LocalParceiroRequest();
        LocalParceiroEntity entity = new LocalParceiroEntity();
        when(localParceiroUseCase.editar(id, request)).thenReturn(entity);

        ResponseEntity<LocalParceiroEntity> response = controller.editar(id, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(localParceiroUseCase).editar(id, request);
    }

    @Test
    void remover_deveRetornarNoContent() {
        Long id = 1L;
        doNothing().when(localParceiroUseCase).remover(id);

        ResponseEntity<Void> response = controller.remover(id);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(localParceiroUseCase).remover(id);
    }
}
package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.PeriodoAcademicoUseCase;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
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

class PeriodoAcademicoControllerTest {

    @Mock
    private PeriodoAcademicoUseCase periodoAcademicoUseCase;

    @InjectMocks
    private PeriodoAcademicoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPeriodoAcademico_found() {
        String rotulo = "P1";
        PeriodoAcademicoEntity entity = new PeriodoAcademicoEntity();
        when(periodoAcademicoUseCase.buscarPeriodoAcademico(rotulo)).thenReturn(Optional.of(entity));

        ResponseEntity<PeriodoAcademicoEntity> response = controller.buscarPeriodoAcademico(rotulo);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void buscarPeriodoAcademico_notFound() {
        String rotulo = "P2";
        when(periodoAcademicoUseCase.buscarPeriodoAcademico(rotulo)).thenReturn(Optional.empty());

        ResponseEntity<PeriodoAcademicoEntity> response = controller.buscarPeriodoAcademico(rotulo);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void listarPeriodos_success() {
        List<PeriodoAcademicoEntity> list = Arrays.asList(new PeriodoAcademicoEntity(), new PeriodoAcademicoEntity());
        when(periodoAcademicoUseCase.listarPeriodos()).thenReturn(list);

        ResponseEntity<List<PeriodoAcademicoEntity>> response = controller.listarPeriodos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    void criarPeriodoAcademico_success() {
        String rotulo = "P3";
        PeriodoAcademicoEntity entity = new PeriodoAcademicoEntity();
        when(periodoAcademicoUseCase.criarPeriodoAcademico(rotulo)).thenReturn(entity);

        ResponseEntity<PeriodoAcademicoEntity> response = controller.criarPeriodoAcademico(rotulo);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
    }

    @Test
    void removerPeriodoAcademico_success() {
        String rotulo = "P4";
        doNothing().when(periodoAcademicoUseCase).removerPeriodoAcademico(rotulo);

        ResponseEntity<Void> response = controller.removerPeriodoAcademico(rotulo);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(periodoAcademicoUseCase, times(1)).removerPeriodoAcademico(rotulo);
    }
}
package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.PeriodoAcademicoEspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PeriodoAcademicoEspecialidadeControllerTest {

    @Mock
    private PeriodoAcademicoEspecialidadeUseCase useCase;

    @InjectMocks
    private PeriodoAcademicoEspecialidadeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDefinirCargaHoraria() {
        String rotulo = "P1";
        Long id = 1L;
        Long cargaHoraria = 60L;
        PeriodoAcademicoEspecialidadeEntity entity = new PeriodoAcademicoEspecialidadeEntity();
        when(useCase.definirCargaHoraria(cargaHoraria, rotulo, id)).thenReturn(entity);

        ResponseEntity<PeriodoAcademicoEspecialidadeEntity> response = controller.definirCargaHoraria(rotulo, id, cargaHoraria);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entity, response.getBody());
        verify(useCase).definirCargaHoraria(cargaHoraria, rotulo, id);
    }

    @Test
    void testListarPeriodoAcademicoEspecialidade() {
        PeriodoAcademicoEspecialidadeEntity entity1 = new PeriodoAcademicoEspecialidadeEntity();
        PeriodoAcademicoEspecialidadeEntity entity2 = new PeriodoAcademicoEspecialidadeEntity();
        List<PeriodoAcademicoEspecialidadeEntity> list = Arrays.asList(entity1, entity2);
        when(useCase.listar()).thenReturn(list);

        ResponseEntity<List<PeriodoAcademicoEspecialidadeEntity>> response = controller.listarPeriodoAcademicoEspecialidade();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
        verify(useCase).listar();
    }
}
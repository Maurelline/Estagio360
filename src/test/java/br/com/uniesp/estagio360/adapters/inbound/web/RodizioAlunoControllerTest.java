package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.RodizioAlunoUseCase;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.request.RodizioAlocarAlunosRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class RodizioAlunoControllerTest {

    private RodizioAlunoUseCase useCase;
    private RodizioAlunoController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(RodizioAlunoUseCase.class);
        controller = new RodizioAlunoController(useCase);
    }

    @Test
    void alocarAlunos_shouldReturnListOfRodizioAlunoEntity() {
        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        RodizioAlunoEntity entity1 = new RodizioAlunoEntity();
        RodizioAlunoEntity entity2 = new RodizioAlunoEntity();
        List<RodizioAlunoEntity> expectedList = Arrays.asList(entity1, entity2);

        when(useCase.alocarAlunos(request)).thenReturn(expectedList);

        ResponseEntity<List<RodizioAlunoEntity>> response = controller.alocarAlunos(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedList, response.getBody());
        verify(useCase, times(1)).alocarAlunos(request);
    }

    @Test
    void alocarAlunos_shouldPassRequestToUseCase() {
        RodizioAlocarAlunosRequest request = new RodizioAlocarAlunosRequest();
        when(useCase.alocarAlunos(any())).thenReturn(List.of());

        controller.alocarAlunos(request);

        ArgumentCaptor<RodizioAlocarAlunosRequest> captor = ArgumentCaptor.forClass(RodizioAlocarAlunosRequest.class);
        verify(useCase).alocarAlunos(captor.capture());
        assertSame(request, captor.getValue());
    }
}
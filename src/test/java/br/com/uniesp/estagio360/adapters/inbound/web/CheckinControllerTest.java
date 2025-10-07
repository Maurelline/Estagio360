package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.CheckinUseCase;
import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import br.com.uniesp.estagio360.domain.request.CheckinRequest;
import br.com.uniesp.estagio360.domain.request.CheckoutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CheckinControllerTest {

    private CheckinUseCase useCase;
    private CheckinController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(CheckinUseCase.class);
        controller = new CheckinController(useCase);
    }

    @Test
    void checkin_shouldReturnOkWithCheckinEntity() {
        CheckinRequest request = new CheckinRequest();
        CheckinEntity entity = new CheckinEntity();
        when(useCase.checkin(ArgumentMatchers.any(CheckinRequest.class))).thenReturn(entity);

        ResponseEntity<CheckinEntity> response = controller.checkin(request);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(entity, response.getBody());
        verify(useCase).checkin(request);
    }

    @Test
    void checkout_shouldReturnOkWithCheckinEntity() {
        CheckoutRequest request = new CheckoutRequest();
        CheckinEntity entity = new CheckinEntity();
        when(useCase.checkout(ArgumentMatchers.any(CheckoutRequest.class))).thenReturn(entity);

        ResponseEntity<CheckinEntity> response = controller.checkout(request);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(entity, response.getBody());
        verify(useCase).checkout(request);
    }
}
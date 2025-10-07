package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.ports.CheckinRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.request.CheckinRequest;
import br.com.uniesp.estagio360.domain.request.CheckoutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckinUseCaseTest {

    private CheckinRepository checkinRepository;
    private RodizioAlunoRepository rodizioAlunoRepository;
    private CheckinUseCase checkinUseCase;

    @BeforeEach
    void setUp() {
        checkinRepository = mock(CheckinRepository.class);
        rodizioAlunoRepository = mock(RodizioAlunoRepository.class);
        checkinUseCase = new CheckinUseCase(checkinRepository, rodizioAlunoRepository);
    }

    @Test
    void checkin_shouldSaveCheckinEntity_whenRodizioAlunoExists() {
        Long rodizioAlunoId = 1L;
        CheckinRequest request = new CheckinRequest();
        request.setRodizioAlunoId(rodizioAlunoId);
        request.setLatitude(10.0);
        request.setLongitude(20.0);

        RodizioAlunoEntity rodizioAluno = new RodizioAlunoEntity();
        when(rodizioAlunoRepository.findById(rodizioAlunoId)).thenReturn(Optional.of(rodizioAluno));

        CheckinEntity savedEntity = new CheckinEntity();
        when(checkinRepository.save(any(CheckinEntity.class))).thenReturn(savedEntity);

        CheckinEntity result = checkinUseCase.checkin(request);

        assertSame(savedEntity, result);
        ArgumentCaptor<CheckinEntity> captor = ArgumentCaptor.forClass(CheckinEntity.class);
        verify(checkinRepository).save(captor.capture());
        CheckinEntity entity = captor.getValue();
        assertEquals(rodizioAluno, entity.getRodizioAluno());
        assertEquals(10.0, entity.getLatitude());
        assertEquals(20.0, entity.getLongitude());
        assertNotNull(entity.getDataHoraCheckin());
    }

    @Test
    void checkin_shouldThrowNotFoundException_whenRodizioAlunoDoesNotExist() {
        Long rodizioAlunoId = 1L;
        CheckinRequest request = new CheckinRequest();
        request.setRodizioAlunoId(rodizioAlunoId);

        when(rodizioAlunoRepository.findById(rodizioAlunoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkinUseCase.checkin(request));
        verify(checkinRepository, never()).save(any());
    }

    @Test
    void checkout_shouldSaveCheckinEntityWithCheckoutTime_whenCheckinExists() {
        Long checkinId = 2L;
        CheckoutRequest request = new CheckoutRequest();
        request.setCheckinId(checkinId);

        CheckinEntity entity = new CheckinEntity();
        when(checkinRepository.findById(checkinId)).thenReturn(Optional.of(entity));
        when(checkinRepository.save(any(CheckinEntity.class))).thenReturn(entity);

        CheckinEntity result = checkinUseCase.checkout(request);

        assertSame(entity, result);
        assertNotNull(entity.getDataHoraCheckout());
        verify(checkinRepository).save(entity);
    }

    @Test
    void checkout_shouldThrowNotFoundException_whenCheckinDoesNotExist() {
        Long checkinId = 2L;
        CheckoutRequest request = new CheckoutRequest();
        request.setCheckinId(checkinId);

        when(checkinRepository.findById(checkinId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkinUseCase.checkout(request));
        verify(checkinRepository, never()).save(any());
    }
}
package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.ports.LocalEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioRepository;
import br.com.uniesp.estagio360.domain.ports.SemestreLetivoRepository;
import br.com.uniesp.estagio360.domain.request.RodizioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RodizioUseCaseTest {

    private RodizioRepository rodizioRepository;
    private LocalEspecialidadeRepository localEspecialidadeRepository;
    private SemestreLetivoRepository semestreLetivoRepository;
    private RodizioUseCase rodizioUseCase;

    @BeforeEach
    void setUp() {
        rodizioRepository = mock(RodizioRepository.class);
        localEspecialidadeRepository = mock(LocalEspecialidadeRepository.class);
        semestreLetivoRepository = mock(SemestreLetivoRepository.class);
        rodizioUseCase = new RodizioUseCase(rodizioRepository, localEspecialidadeRepository, semestreLetivoRepository);
    }

    @Test
    void criar_deveSalvarERetornarRodizioEntity() {
        Long localEspecialidadeId = 1L;
        Long semestreLetivoId = 2L;
        LocalEspecialidadeEntity localEspecialidade = new LocalEspecialidadeEntity();
        SemestreLetivoEntity semestreLetivo = new SemestreLetivoEntity();

        RodizioRequest request = new RodizioRequest();
        request.setLocalEspecialidadeId(localEspecialidadeId);
        request.setSemestreLetivoId(semestreLetivoId);
        request.setData(LocalDate.of(2024, 6, 1));
        request.setHoraInicio(LocalTime.of(8, 0));
        request.setHoraFim(LocalTime.of(12, 0));
        request.setVagas(10);

        when(localEspecialidadeRepository.findById(localEspecialidadeId)).thenReturn(Optional.of(localEspecialidade));
        when(semestreLetivoRepository.findById(semestreLetivoId)).thenReturn(Optional.of(semestreLetivo));
        when(rodizioRepository.save(any(RodizioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RodizioEntity result = rodizioUseCase.criar(request);

        assertNotNull(result);
        assertEquals(localEspecialidade, result.getLocalEspecialidade());
        assertEquals(semestreLetivo, result.getSemestreLetivo());
        assertEquals(request.getData(), result.getData());
        assertEquals(request.getHoraInicio(), result.getHoraInicio());
        assertEquals(request.getHoraFim(), result.getHoraFim());
        assertEquals(request.getVagas(), result.getVagas());

        verify(rodizioRepository).save(any(RodizioEntity.class));
    }

    @Test
    void criar_deveLancarNotFoundException_QuandoLocalEspecialidadeNaoEncontrado() {
        Long localEspecialidadeId = 1L;
        RodizioRequest request = new RodizioRequest();
        request.setLocalEspecialidadeId(localEspecialidadeId);

        when(localEspecialidadeRepository.findById(localEspecialidadeId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> rodizioUseCase.criar(request));
        assertTrue(ex.getMessage().contains("LocalEspecialidade"));
    }

    @Test
    void criar_deveLancarNotFoundException_QuandoSemestreLetivoNaoEncontrado() {
        Long localEspecialidadeId = 1L;
        Long semestreLetivoId = 2L;
        LocalEspecialidadeEntity localEspecialidade = new LocalEspecialidadeEntity();
        RodizioRequest request = new RodizioRequest();
        request.setLocalEspecialidadeId(localEspecialidadeId);
        request.setSemestreLetivoId(semestreLetivoId);

        when(localEspecialidadeRepository.findById(localEspecialidadeId)).thenReturn(Optional.of(localEspecialidade));
        when(semestreLetivoRepository.findById(semestreLetivoId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> rodizioUseCase.criar(request));
        assertTrue(ex.getMessage().contains("SemestreLetivo"));
    }

    @Test
    void listar_deveRetornarListaDeRodizioEntity() {
        RodizioEntity entity1 = new RodizioEntity();
        RodizioEntity entity2 = new RodizioEntity();
        when(rodizioRepository.findAll()).thenReturn(List.of(entity1, entity2));

        List<RodizioEntity> result = rodizioUseCase.listar();

        assertEquals(2, result.size());
        assertTrue(result.contains(entity1));
        assertTrue(result.contains(entity2));
        verify(rodizioRepository).findAll();
    }
}
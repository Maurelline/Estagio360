package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PeriodoAcademicoEspecialidadeUseCaseTest {

    @Mock
    private PeriodoAcademicoEspecialidadeRepository periodoAcademicoEspecialidadeRepository;
    @Mock
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private PeriodoAcademicoEspecialidadeUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new PeriodoAcademicoEspecialidadeUseCase(periodoAcademicoEspecialidadeRepository);
        try {
            var periodoField = PeriodoAcademicoEspecialidadeUseCase.class.getDeclaredField("periodoAcademicoRepository");
            periodoField.setAccessible(true);
            periodoField.set(useCase, periodoAcademicoRepository);

            var especialidadeField = PeriodoAcademicoEspecialidadeUseCase.class.getDeclaredField("especialidadeRepository");
            especialidadeField.setAccessible(true);
            especialidadeField.set(useCase, especialidadeRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void definirCargaHoraria_shouldSaveAndReturnEntity() {
        Long cargaHoraria = 100L;
        String rotulo = "P1";
        Long idEspecialidade = 1L;

        PeriodoAcademicoEntity periodoEntity = new PeriodoAcademicoEntity();
        periodoEntity.setRotulo("P1");
        EspecialidadeEntity especialidadeEntity = new EspecialidadeEntity();
        especialidadeEntity.setId(1L);

        when(periodoAcademicoRepository.findById(rotulo)).thenReturn(Optional.of(periodoEntity));
        when(especialidadeRepository.findById(idEspecialidade)).thenReturn(Optional.of(especialidadeEntity));

        var entityToSave = ArgumentCaptor.forClass(PeriodoAcademicoEspecialidadeEntity.class);
        var savedEntity = new PeriodoAcademicoEspecialidadeEntity();
        when(periodoAcademicoEspecialidadeRepository.save(any())).thenReturn(savedEntity);

        var result = useCase.definirCargaHoraria(cargaHoraria, rotulo, idEspecialidade);

        assertEquals(savedEntity, result);
        verify(periodoAcademicoRepository).findById(rotulo);
        verify(especialidadeRepository).findById(idEspecialidade);
        verify(periodoAcademicoEspecialidadeRepository).save(entityToSave.capture());

        var captured = entityToSave.getValue();
        assertEquals(cargaHoraria, captured.getCargaHoraria());
        assertEquals(periodoEntity, captured.getPeriodoAcademico());
        assertEquals(especialidadeEntity, captured.getEspecialidade());
    }

    @Test
    void definirCargaHoraria_shouldThrowNotFoundException_whenPeriodoNotFound() {
        String rotulo = "P1";
        Long idEspecialidade = 1L;

        when(periodoAcademicoRepository.findById(rotulo)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                useCase.definirCargaHoraria(100L, rotulo, idEspecialidade)
        );
        verify(periodoAcademicoRepository).findById(rotulo);
        verifyNoInteractions(especialidadeRepository);
        verifyNoInteractions(periodoAcademicoEspecialidadeRepository);
    }

    @Test
    void definirCargaHoraria_shouldThrowNotFoundException_whenEspecialidadeNotFound() {
        String rotulo = "P1";
        Long idEspecialidade = 1L;

        PeriodoAcademicoEntity periodoEntity = new PeriodoAcademicoEntity();
        periodoEntity.setRotulo("P1");

        when(periodoAcademicoRepository.findById(rotulo)).thenReturn(Optional.of(periodoEntity));
        when(especialidadeRepository.findById(idEspecialidade)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                useCase.definirCargaHoraria(100L, rotulo, idEspecialidade)
        );
        verify(periodoAcademicoRepository).findById(rotulo);
        verify(especialidadeRepository).findById(idEspecialidade);
        verifyNoInteractions(periodoAcademicoEspecialidadeRepository);
    }

    @Test
    void listar_shouldReturnAllEntities() {
        var entities = List.of(new PeriodoAcademicoEspecialidadeEntity(), new PeriodoAcademicoEspecialidadeEntity());
        when(periodoAcademicoEspecialidadeRepository.findAll()).thenReturn(entities);

        var result = useCase.listar();

        assertEquals(entities, result);
        verify(periodoAcademicoEspecialidadeRepository).findAll();
    }
}
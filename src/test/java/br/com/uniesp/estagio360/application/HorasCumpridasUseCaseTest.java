package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.*;
import br.com.uniesp.estagio360.domain.ports.CheckinRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.response.HorasCumpridasRelatorioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HorasCumpridasUseCaseTest {

    private RodizioAlunoRepository rodizioAlunoRepository;
    private CheckinRepository checkinRepository;
    private PeriodoAcademicoEspecialidadeRepository periodoAcademicoEspecialidadeRepository;
    private HorasCumpridasUseCase useCase;

    @BeforeEach
    void setUp() {
        rodizioAlunoRepository = mock(RodizioAlunoRepository.class);
        checkinRepository = mock(CheckinRepository.class);
        periodoAcademicoEspecialidadeRepository = mock(PeriodoAcademicoEspecialidadeRepository.class);
        useCase = new HorasCumpridasUseCase(
                rodizioAlunoRepository,
                checkinRepository,
                periodoAcademicoEspecialidadeRepository
        );
    }

    @Test
    void calcularHorasCumpridas_returnsCorrectDTO_whenDataIsValid() {
        Long alunoId = 1L;
        Long especialidadeId = 2L;
        String periodoRotulo = "P1";

        EspecialidadeEntity especialidade = new EspecialidadeEntity();
        especialidade.setId(especialidadeId);

        LocalEspecialidadeEntity localEspecialidade = new LocalEspecialidadeEntity();
        localEspecialidade.setEspecialidade(especialidade);

        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setLocalEspecialidade(localEspecialidade);

        RodizioAlunoEntity rodizioAluno = new RodizioAlunoEntity();
        rodizioAluno.setId(10L);
        rodizioAluno.setRodizio(rodizio);

        when(rodizioAlunoRepository.findByAlunoId(alunoId))
                .thenReturn(List.of(rodizioAluno));

        CheckinEntity checkin = new CheckinEntity();
        checkin.setDataHoraCheckin(LocalDateTime.of(2024, 6, 1, 8, 0));
        checkin.setDataHoraCheckout(LocalDateTime.of(2024, 6, 1, 12, 0));

        when(checkinRepository.findAllByRodizioAlunoIdIn(List.of(10L)))
                .thenReturn(List.of(checkin));

        PeriodoAcademicoEspecialidadeEntity pae = new PeriodoAcademicoEspecialidadeEntity();
        pae.setCargaHoraria(3L);

        when(periodoAcademicoEspecialidadeRepository
                .findByPeriodoAcademico_RotuloAndEspecialidade_Id(periodoRotulo, especialidadeId))
                .thenReturn(Optional.of(pae));

        HorasCumpridasRelatorioDTO dto = useCase.calcularHorasCumpridas(alunoId, especialidadeId, periodoRotulo);

        assertEquals(alunoId, dto.getAlunoId());
        assertEquals(especialidadeId, dto.getEspecialidadeId());
        assertEquals(4L, dto.getHorasCumpridas());
        assertEquals(3L, dto.getHorasObrigatorias());
        assertTrue(dto.isCumpriuObrigatorio());
    }

    @Test
    void calcularHorasCumpridas_throwsNotFoundException_whenPeriodoAcademicoEspecialidadeNotFound() {
        Long alunoId = 1L;
        Long especialidadeId = 2L;
        String periodoRotulo = "P1";

        EspecialidadeEntity especialidade = new EspecialidadeEntity();
        especialidade.setId(especialidadeId);

        LocalEspecialidadeEntity localEspecialidade = new LocalEspecialidadeEntity();
        localEspecialidade.setEspecialidade(especialidade);

        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setLocalEspecialidade(localEspecialidade);

        RodizioAlunoEntity rodizioAluno = new RodizioAlunoEntity();
        rodizioAluno.setId(10L);
        rodizioAluno.setRodizio(rodizio);

        when(rodizioAlunoRepository.findByAlunoId(alunoId))
                .thenReturn(List.of(rodizioAluno));

        when(checkinRepository.findAllByRodizioAlunoIdIn(List.of(10L)))
                .thenReturn(List.of());

        when(periodoAcademicoEspecialidadeRepository
                .findByPeriodoAcademico_RotuloAndEspecialidade_Id(periodoRotulo, especialidadeId))
                .thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () ->
                useCase.calcularHorasCumpridas(alunoId, especialidadeId, periodoRotulo));
    }

    @Test
    void calcularHorasCumpridas_handlesCheckinsWithNullDates() {
        Long alunoId = 1L;
        Long especialidadeId = 2L;
        String periodoRotulo = "P1";


        EspecialidadeEntity especialidade = new EspecialidadeEntity();
        especialidade.setId(especialidadeId);

        LocalEspecialidadeEntity localEspecialidade = new LocalEspecialidadeEntity();
        localEspecialidade.setEspecialidade(especialidade);

        RodizioEntity rodizio = new RodizioEntity();
        rodizio.setLocalEspecialidade(localEspecialidade);

        RodizioAlunoEntity rodizioAluno = new RodizioAlunoEntity();
        rodizioAluno.setId(10L);
        rodizioAluno.setRodizio(rodizio);

        when(rodizioAlunoRepository.findByAlunoId(alunoId))
                .thenReturn(List.of(rodizioAluno));

        CheckinEntity checkin1 = new CheckinEntity();
        checkin1.setDataHoraCheckin(null);
        checkin1.setDataHoraCheckout(null);

        CheckinEntity checkin2 = new CheckinEntity();
        checkin2.setDataHoraCheckin(LocalDateTime.of(2024, 6, 1, 8, 0));
        checkin2.setDataHoraCheckout(LocalDateTime.of(2024, 6, 1, 10, 0));

        when(checkinRepository.findAllByRodizioAlunoIdIn(List.of(10L)))
                .thenReturn(List.of(checkin1, checkin2));

        PeriodoAcademicoEspecialidadeEntity pae = new PeriodoAcademicoEspecialidadeEntity();
        pae.setCargaHoraria(5L);

        when(periodoAcademicoEspecialidadeRepository
                .findByPeriodoAcademico_RotuloAndEspecialidade_Id(periodoRotulo, especialidadeId))
                .thenReturn(Optional.of(pae));

        HorasCumpridasRelatorioDTO dto = useCase.calcularHorasCumpridas(alunoId, especialidadeId, periodoRotulo);

        assertEquals(2L, dto.getHorasCumpridas());
        assertFalse(dto.isCumpriuObrigatorio());
    }
}
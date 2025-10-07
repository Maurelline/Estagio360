package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.ports.CheckinRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.response.HorasCumpridasRelatorioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorasCumpridasUseCase {

    private final RodizioAlunoRepository rodizioAlunoRepository;
    private final CheckinRepository checkinRepository;
    private final PeriodoAcademicoEspecialidadeRepository periodoAcademicoEspecialidadeRepository;

    public HorasCumpridasRelatorioDTO calcularHorasCumpridas(Long alunoId, Long especialidadeId, String periodoRotulo) {
        List<RodizioAlunoEntity> rodiziosAluno = rodizioAlunoRepository.findByAlunoId(alunoId);

        List<Long> rodizioAlunoIds = rodiziosAluno.stream()
                .filter(ra -> ra.getRodizio().getLocalEspecialidade().getEspecialidade().getId().equals(especialidadeId))
                .map(RodizioAlunoEntity::getId)
                .toList();

        List<CheckinEntity> checkins = checkinRepository.findAllByRodizioAlunoIdIn(rodizioAlunoIds);

        long horasCumpridas = checkins.stream()
                .filter(c -> c.getDataHoraCheckin() != null && c.getDataHoraCheckout() != null)
                .mapToLong(c -> java.time.Duration.between(c.getDataHoraCheckin(), c.getDataHoraCheckout()).toHours())
                .sum();

        PeriodoAcademicoEspecialidadeEntity pae = periodoAcademicoEspecialidadeRepository
                .findByPeriodoAcademico_RotuloAndEspecialidade_Id(periodoRotulo, especialidadeId)
                .orElseThrow(() -> new NotFoundException("PeriodoAcademicoEspecialidade", periodoRotulo + "-" + especialidadeId));

        long horasObrigatorias = pae.getCargaHoraria();

        HorasCumpridasRelatorioDTO dto = new HorasCumpridasRelatorioDTO();
        dto.setAlunoId(alunoId);
        dto.setEspecialidadeId(especialidadeId);
        dto.setHorasCumpridas(horasCumpridas);
        dto.setHorasObrigatorias(horasObrigatorias);
        dto.setCumpriuObrigatorio(horasCumpridas >= horasObrigatorias);

        return dto;
    }
}
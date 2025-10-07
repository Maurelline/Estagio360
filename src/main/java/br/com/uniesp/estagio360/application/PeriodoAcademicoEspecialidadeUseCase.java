package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoEspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodoAcademicoEspecialidadeUseCase {
    private final PeriodoAcademicoEspecialidadeRepository periodoAcademicoEspecialidadeRepository;
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    private EspecialidadeRepository especialidadeRepository;

    public PeriodoAcademicoEspecialidadeEntity definirCargaHoraria(Long cargaHoraria, String rotulo, Long idEspecialidade) {
        var periodoEntity = periodoAcademicoRepository.findById(rotulo).orElseThrow((() -> new NotFoundException("Periodo Academico", rotulo)));
        var especialidadeEntity = especialidadeRepository.findById(idEspecialidade).orElseThrow(() -> new NotFoundException("Especialidade", idEspecialidade.toString()));

        var entity = new PeriodoAcademicoEspecialidadeEntity();
        entity.setCargaHoraria(cargaHoraria);
        entity.setPeriodoAcademico(periodoEntity);
        entity.setEspecialidade(especialidadeEntity);

        return periodoAcademicoEspecialidadeRepository.save(entity);
    }

    public List<PeriodoAcademicoEspecialidadeEntity> listar() {
        return periodoAcademicoEspecialidadeRepository.findAll();
    }
}

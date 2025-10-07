package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodoAcademicoUseCase {

    private final PeriodoAcademicoRepository periodoAcademicoRepository;


    public Optional<PeriodoAcademicoEntity> buscarPeriodoAcademico(String rotulo) {
        return periodoAcademicoRepository.findById(rotulo);
    }

    public List<PeriodoAcademicoEntity> listarPeriodos() {
        return periodoAcademicoRepository.findAll();
    }

    public PeriodoAcademicoEntity criarPeriodoAcademico(String rotulo) {
        var entity = new PeriodoAcademicoEntity();
        entity.setRotulo(rotulo);

        return periodoAcademicoRepository.save(entity);
    }

    public void removerPeriodoAcademico(String rotulo) {
        periodoAcademicoRepository.deleteById(rotulo);
    }
}

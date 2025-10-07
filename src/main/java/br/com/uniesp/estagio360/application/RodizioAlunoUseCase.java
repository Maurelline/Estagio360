package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.ports.RodizioAlunoRepository;
import br.com.uniesp.estagio360.domain.ports.RodizioRepository;
import br.com.uniesp.estagio360.domain.ports.UsuarioRepository;
import br.com.uniesp.estagio360.domain.request.RodizioAlocarAlunosRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RodizioAlunoUseCase {
    private final RodizioAlunoRepository rodizioAlunoRepository;
    private final RodizioRepository rodizioRepository;
    private final UsuarioRepository usuarioRepository;

    public List<RodizioAlunoEntity> alocarAlunos(RodizioAlocarAlunosRequest request) {
        RodizioEntity rodizio = rodizioRepository.findById(request.getRodizioId())
                .orElseThrow(() -> new NotFoundException("Rodizio", request.getRodizioId().toString()));

        long vagasOcupadas = rodizioAlunoRepository.countByRodizioId(rodizio.getId());
        int vagasDisponiveis = rodizio.getVagas() - (int) vagasOcupadas;

        List<RodizioAlunoEntity> alocados = new ArrayList<>();
        for (Long alunoId : request.getAlunosIds()) {
            if (vagasDisponiveis <= 0) break;
            if (rodizioAlunoRepository.existsByRodizioIdAndAlunoId(rodizio.getId(), alunoId)) continue;

            UsuarioEntity aluno = usuarioRepository.findById(alunoId)
                    .orElseThrow(() -> new NotFoundException("Aluno", alunoId.toString()));

            RodizioAlunoEntity entity = new RodizioAlunoEntity();
            entity.setRodizio(rodizio);
            entity.setAluno(aluno);
            alocados.add(rodizioAlunoRepository.save(entity));
            vagasDisponiveis--;
        }
        return alocados;
    }
}
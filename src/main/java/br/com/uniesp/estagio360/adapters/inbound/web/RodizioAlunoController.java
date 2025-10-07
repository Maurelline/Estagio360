package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.RodizioAlunoUseCase;
import br.com.uniesp.estagio360.domain.model.RodizioAlunoEntity;
import br.com.uniesp.estagio360.domain.request.RodizioAlocarAlunosRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/rodizio-aluno")
@RequiredArgsConstructor
@Tag(name = "Rodízio Aluno", description = "Operações de alocação de alunos em rodízios (plantões)")
public class RodizioAlunoController {
    private final RodizioAlunoUseCase useCase;

    @Operation(
            summary = "Alocar alunos em rodízio",
            description = "Aloca (escala) alunos em um rodízio (plantão). Para cada aluno selecionado, cria um registro em RodizioAluno. O sistema checa se ainda há vagas disponíveis e não permite adicionar além do limite. Pode usar filtros para priorizar quem ainda precisa de mais horas na especialidade."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alunos alocados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou limite de vagas atingido"),
            @ApiResponse(responseCode = "404", description = "Rodízio ou aluno não encontrado")
    })
    @PostMapping("/alocar")
    public ResponseEntity<List<RodizioAlunoEntity>> alocarAlunos(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para alocação dos alunos no rodízio")
            @RequestBody
            RodizioAlocarAlunosRequest request
    ) {
        return ResponseEntity.ok(useCase.alocarAlunos(request));
    }
}
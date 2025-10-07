package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.PeriodoAcademicoUseCase;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/periodo-academico")
@RequiredArgsConstructor
@Tag(name = "Período Acadêmico", description = "Operações relacionadas aos períodos acadêmicos")
public class PeriodoAcademicoController {

    private final PeriodoAcademicoUseCase periodoAcademicoUseCase;

    @Operation(
            summary = "Buscar período acadêmico por rótulo",
            description = "Retorna um período acadêmico pelo seu rótulo (identificador único)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Período acadêmico encontrado"),
            @ApiResponse(responseCode = "404", description = "Período acadêmico não encontrado")
    })
    @GetMapping("/{rotulo}")
    public ResponseEntity<PeriodoAcademicoEntity> buscarPeriodoAcademico(
            @Parameter(description = "Rótulo do período acadêmico") @PathVariable String rotulo) {
        var periodoAcademico = periodoAcademicoUseCase.buscarPeriodoAcademico(rotulo);
        if (periodoAcademico.isPresent()) {
            return ResponseEntity.ok(periodoAcademico.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Listar períodos acadêmicos",
            description = "Retorna uma lista de todos os períodos acadêmicos cadastrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista de períodos acadêmicos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PeriodoAcademicoEntity>> listarPeriodos() {
        var periodos = periodoAcademicoUseCase.listarPeriodos();
        return ResponseEntity.ok(periodos);
    }

    @Operation(
            summary = "Criar período acadêmico",
            description = "Cria um novo período acadêmico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Período acadêmico criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PeriodoAcademicoEntity> criarPeriodoAcademico(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Rótulo do período acadêmico a ser criado")
            @RequestParam("rotulo_periodo") String rotulo) {
        var periodoAcademico = periodoAcademicoUseCase.criarPeriodoAcademico(rotulo);
        return ResponseEntity.ok(periodoAcademico);
    }

    @Operation(
            summary = "Remover período acadêmico",
            description = "Remove um período acadêmico pelo rótulo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Período acadêmico removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Período acadêmico não encontrado")
    })
    @DeleteMapping("/{rotulo}")
    public ResponseEntity<Void> removerPeriodoAcademico(
            @Parameter(description = "Rótulo do período acadêmico") @PathVariable String rotulo) {
        periodoAcademicoUseCase.removerPeriodoAcademico(rotulo);
        return ResponseEntity.noContent().build();
    }
}
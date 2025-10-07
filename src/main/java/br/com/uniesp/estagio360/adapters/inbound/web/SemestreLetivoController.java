package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.SemestreLetivoUseCase;
import br.com.uniesp.estagio360.domain.model.SemestreLetivoEntity;
import br.com.uniesp.estagio360.domain.request.SemestreLetivoRequest;
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
@RequestMapping("/v1/semestre-letivo")
@RequiredArgsConstructor
@Tag(name = "Semestre Letivo", description = "Operações relacionadas aos semestres letivos")
public class SemestreLetivoController {

    private final SemestreLetivoUseCase semestreLetivoUseCase;

    @Operation(summary = "Buscar semestre letivo por ID", description = "Retorna um semestre letivo pelo seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semestre letivo encontrado"),
            @ApiResponse(responseCode = "404", description = "Semestre letivo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SemestreLetivoEntity> buscarSemestrePorID(
            @Parameter(description = "ID do semestre letivo") @PathVariable Long id) {
        var semestre = semestreLetivoUseCase.buscarSemestrePorID(id);
        if (semestre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(semestre.get());
    }

    @Operation(summary = "Listar semestres letivos", description = "Retorna uma lista de todos os semestres letivos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de semestres letivos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SemestreLetivoEntity>> listarSemestreLetivo() {
        var semestres = semestreLetivoUseCase.listarSemestreLetivo();
        return ResponseEntity.ok(semestres);
    }

    @Operation(summary = "Criar semestre letivo", description = "Cria um novo semestre letivo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semestre letivo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<SemestreLetivoEntity> criarSemestreLetivo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do semestre letivo a ser criado")
            @RequestBody SemestreLetivoRequest request) {
        var semestre = semestreLetivoUseCase.criarSemestreLetivo(request);
        return ResponseEntity.ok(semestre);
    }

    @Operation(summary = "Editar semestre letivo", description = "Edita um semestre letivo existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semestre letivo editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Semestre letivo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SemestreLetivoEntity> editarSemestreLetivo(
            @Parameter(description = "ID do semestre letivo") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização do semestre letivo")
            @RequestBody SemestreLetivoRequest request) {
        var semestre = semestreLetivoUseCase.editarSemestreLetivo(id, request);
        return ResponseEntity.ok(semestre);
    }

    @Operation(summary = "Remover semestre letivo", description = "Remove um semestre letivo pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Semestre letivo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Semestre letivo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerSemestreLetivo(
            @Parameter(description = "ID do semestre letivo") @PathVariable Long id) {
        semestreLetivoUseCase.removerSemestreLetivo(id);
        return ResponseEntity.noContent().build();
    }
}
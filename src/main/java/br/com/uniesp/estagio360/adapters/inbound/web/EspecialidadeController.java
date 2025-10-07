package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.EspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.request.EspecialidadeRequest;
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
@RequestMapping("/v1/especialidade")
@RequiredArgsConstructor
@Tag(name = "Especialidade", description = "Operações relacionadas às especialidades")
public class EspecialidadeController {

    private final EspecialidadeUseCase especialidadeUseCase;

    @Operation(summary = "Buscar especialidade por ID", description = "Retorna uma especialidade pelo seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeEntity> buscarEspecialidadePorID(
            @Parameter(description = "ID da especialidade") @PathVariable Long id) {
        var especialidade = especialidadeUseCase.buscarEspecialidadePorID(id);
        if (especialidade.isPresent()) {
            return ResponseEntity.ok(especialidade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar especialidades", description = "Retorna uma lista de todas as especialidades cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de especialidades retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EspecialidadeEntity>> listarEspecialidades() {
        var especialidades = especialidadeUseCase.listarEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    @Operation(summary = "Criar especialidade", description = "Cria uma nova especialidade.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EspecialidadeEntity> criarEspecialidade(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da especialidade a ser criada")
            @RequestBody EspecialidadeRequest request) {
        var especialidade = especialidadeUseCase.criarEspecialidade(request);
        return ResponseEntity.ok(especialidade);
    }

    @Operation(summary = "Editar especialidade", description = "Edita uma especialidade existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Especialidade editada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeEntity> editarEspecialidade(
            @Parameter(description = "ID da especialidade") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização da especialidade")
            @RequestBody EspecialidadeRequest request) {
        var especialidade = especialidadeUseCase.editarEspecialidade(id, request);
        return ResponseEntity.ok(especialidade);
    }

    @Operation(summary = "Remover especialidade", description = "Remove uma especialidade pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Especialidade removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EspecialidadeEntity> removerEspecialidade(
            @Parameter(description = "ID da especialidade") @PathVariable Long id) {
        especialidadeUseCase.removerEspecialidade(id);
        return ResponseEntity.noContent().build();
    }
}
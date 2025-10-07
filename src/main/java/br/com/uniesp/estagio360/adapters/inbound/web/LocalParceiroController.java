package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.LocalParceiroUseCase;
import br.com.uniesp.estagio360.domain.model.LocalParceiroEntity;
import br.com.uniesp.estagio360.domain.request.LocalParceiroRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/local-parceiro")
@RequiredArgsConstructor
@Tag(name = "Local Parceiro", description = "Operações relacionadas aos locais de parceiros")
public class LocalParceiroController {
    private final LocalParceiroUseCase localParceiroUseCase;

    @Operation(summary = "Criar local parceiro", description = "Cria um novo local parceiro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local parceiro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<LocalParceiroEntity> criar(
            @RequestBody(description = "Dados do local parceiro a ser criado")
            @org.springframework.web.bind.annotation.RequestBody LocalParceiroRequest request) {
        return ResponseEntity.ok(localParceiroUseCase.criar(request));
    }

    @Operation(summary = "Listar locais parceiros", description = "Retorna uma lista de todos os locais parceiros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de locais parceiros retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<LocalParceiroEntity>> listar() {
        return ResponseEntity.ok(localParceiroUseCase.listar());
    }

    @Operation(summary = "Buscar local parceiro por ID", description = "Retorna um local parceiro pelo seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local parceiro encontrado"),
            @ApiResponse(responseCode = "404", description = "Local parceiro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocalParceiroEntity> buscarPorId(
            @PathVariable Long id) {
        return localParceiroUseCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Editar local parceiro", description = "Edita um local parceiro existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local parceiro editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local parceiro não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalParceiroEntity> editar(
            @PathVariable Long id,
            @RequestBody(description = "Dados para atualização do local parceiro")
            @org.springframework.web.bind.annotation.RequestBody LocalParceiroRequest request) {
        return ResponseEntity.ok(localParceiroUseCase.editar(id, request));
    }

    @Operation(summary = "Remover local parceiro", description = "Remove um local parceiro pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Local parceiro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local parceiro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        localParceiroUseCase.remover(id);
        return ResponseEntity.noContent().build();
    }
}
package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.RodizioUseCase;
import br.com.uniesp.estagio360.domain.model.RodizioEntity;
import br.com.uniesp.estagio360.domain.request.RodizioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rodizio")
@RequiredArgsConstructor
@Tag(name = "Rodízio", description = "Operações relacionadas aos rodízios (plantões)")
public class RodizioController {
    private final RodizioUseCase useCase;

    @Operation(summary = "Criar rodízio", description = "Cria um novo rodízio (plantão) para o semestre.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rodízio criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<RodizioEntity> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do rodízio a ser criado")
            @RequestBody RodizioRequest request
    ) {
        return ResponseEntity.ok(useCase.criar(request));
    }

    @Operation(summary = "Listar rodízios", description = "Retorna uma lista de todos os rodízios cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de rodízios retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<RodizioEntity>> listar() {
        return ResponseEntity.ok(useCase.listar());
    }
}
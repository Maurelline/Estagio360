package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.CheckinUseCase;
import br.com.uniesp.estagio360.domain.model.CheckinEntity;
import br.com.uniesp.estagio360.domain.request.CheckinRequest;
import br.com.uniesp.estagio360.domain.request.CheckoutRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/checkin")
@RequiredArgsConstructor
@Tag(name = "Check-in / Check-out", description = "Operações de check-in e check-out dos alunos nos rodízios")
public class CheckinController {

    private final CheckinUseCase useCase;

    @Operation(
            summary = "Realizar check-in",
            description = "Registra o check-in de um aluno em um rodízio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rodízio do aluno não encontrado")
    })
    @PostMapping
    public ResponseEntity<CheckinEntity> checkin(
            @RequestBody(description = "Dados para realizar o check-in")
            @org.springframework.web.bind.annotation.RequestBody CheckinRequest request
    ) {
        return ResponseEntity.ok(useCase.checkin(request));
    }

    @Operation(
            summary = "Realizar check-out",
            description = "Registra o check-out de um aluno em um rodízio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-out realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado")
    })
    @PostMapping("/checkout")
    public ResponseEntity<CheckinEntity> checkout(
            @RequestBody(description = "Dados para realizar o check-out")
            @org.springframework.web.bind.annotation.RequestBody CheckoutRequest request
    ) {
        return ResponseEntity.ok(useCase.checkout(request));
    }
}
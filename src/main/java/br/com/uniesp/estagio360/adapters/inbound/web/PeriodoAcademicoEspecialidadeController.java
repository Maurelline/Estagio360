package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.PeriodoAcademicoEspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEspecialidadeEntity;
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
@RequestMapping("/v1/carga-horaria")
@RequiredArgsConstructor
@Tag(name = "Carga Horária", description = "Operações relacionadas à carga horária de períodos acadêmicos e especialidades")
public class PeriodoAcademicoEspecialidadeController {

    private final PeriodoAcademicoEspecialidadeUseCase periodoAcademicoEspecialidadeUseCase;

    @Operation(
            summary = "Definir carga horária",
            description = "Define a carga horária para uma especialidade em um período acadêmico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carga horária definida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Período acadêmico ou especialidade não encontrados"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/periodo/{rotulo}/especialidade/{id}/cadastrar")
    public ResponseEntity<PeriodoAcademicoEspecialidadeEntity> definirCargaHoraria(
            @Parameter(description = "Rótulo do período acadêmico") @PathVariable String rotulo,
            @Parameter(description = "ID da especialidade") @PathVariable Long id,
            @Parameter(description = "Carga horária a ser definida") @RequestParam("carga_horaria") Long cargaHoraria) {
        var periodoAcademicoEspecialidade = periodoAcademicoEspecialidadeUseCase.definirCargaHoraria(cargaHoraria, rotulo, id);
        return ResponseEntity.ok(periodoAcademicoEspecialidade);
    }

    @Operation(
            summary = "Listar cargas horárias",
            description = "Lista todas as relações de carga horária entre períodos acadêmicos e especialidades."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/periodo/especialidade")
    public ResponseEntity<List<PeriodoAcademicoEspecialidadeEntity>> listarPeriodoAcademicoEspecialidade() {
        var periodoAcademicoEspecialidadeEntityList = periodoAcademicoEspecialidadeUseCase.listar();
        return ResponseEntity.ok(periodoAcademicoEspecialidadeEntityList);
    }
}
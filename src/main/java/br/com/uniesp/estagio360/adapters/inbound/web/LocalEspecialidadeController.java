package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.LocalEspecialidadeUseCase;
import br.com.uniesp.estagio360.domain.model.LocalEspecialidadeEntity;
import br.com.uniesp.estagio360.domain.request.LocalEspecialidadeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/local-especialidade")
@RequiredArgsConstructor
@Tag(name = "Local Especialidade", description = "Operações relacionadas à associação de locais parceiros e especialidades")
public class LocalEspecialidadeController {
    private final LocalEspecialidadeUseCase useCase;

    @Operation(summary = "Criar LocalEspecialidade", description = "Associa um local parceiro a uma especialidade, informando a quantidade de vagas e, opcionalmente, o semestre letivo.")
    @PostMapping
    public ResponseEntity<LocalEspecialidadeEntity> criar(@RequestBody LocalEspecialidadeRequest request) {
        return ResponseEntity.ok(useCase.criar(request));
    }

    @Operation(summary = "Listar LocalEspecialidade", description = "Lista todas as associações de locais parceiros e especialidades.")
    @GetMapping
    public ResponseEntity<List<LocalEspecialidadeEntity>> listar() {
        return ResponseEntity.ok(useCase.listar());
    }
}
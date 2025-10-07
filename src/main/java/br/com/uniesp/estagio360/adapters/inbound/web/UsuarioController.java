package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.UsuarioUseCase;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.request.UsuarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Operações relacionadas aos usuários")
public class UsuarioController {
    private final UsuarioUseCase usuarioUseCase;

    @Operation(summary = "Criar usuário", description = "Cria um novo usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioEntity> criarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário a ser criado")
            @RequestBody UsuarioRequest request) {
        var usuario = usuarioUseCase.criarUsuario(request);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário pelo seu identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        Optional<UsuarioEntity> usuario = usuarioUseCase.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> listarUsuarios() {
        List<UsuarioEntity> usuarios = usuarioUseCase.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Editar usuário", description = "Edita um usuário existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> editarUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização do usuário")
            @RequestBody UsuarioRequest request) {
        UsuarioEntity usuario = usuarioUseCase.editarUsuario(id, request);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Remover usuário", description = "Remove um usuário pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        usuarioUseCase.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
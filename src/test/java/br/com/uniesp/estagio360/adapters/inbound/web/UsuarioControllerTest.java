package br.com.uniesp.estagio360.adapters.inbound.web;

import br.com.uniesp.estagio360.application.UsuarioUseCase;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.request.UsuarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarUsuario_deveRetornarUsuarioCriado() {
        UsuarioRequest request = new UsuarioRequest();
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioUseCase.criarUsuario(request)).thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.criarUsuario(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioUseCase).criarUsuario(request);
    }

    @Test
    void buscarUsuarioPorId_usuarioEncontrado() {
        Long id = 1L;
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioUseCase.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioEntity> response = usuarioController.buscarUsuarioPorId(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioUseCase).buscarUsuarioPorId(id);
    }

    @Test
    void buscarUsuarioPorId_usuarioNaoEncontrado() {
        Long id = 1L;
        when(usuarioUseCase.buscarUsuarioPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioEntity> response = usuarioController.buscarUsuarioPorId(id);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(usuarioUseCase).buscarUsuarioPorId(id);
    }

    @Test
    void listarUsuarios_deveRetornarListaDeUsuarios() {
        List<UsuarioEntity> usuarios = List.of(new UsuarioEntity(), new UsuarioEntity());
        when(usuarioUseCase.listarUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioEntity>> response = usuarioController.listarUsuarios();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarios, response.getBody());
        verify(usuarioUseCase).listarUsuarios();
    }

    @Test
    void editarUsuario_deveRetornarUsuarioEditado() {
        Long id = 1L;
        UsuarioRequest request = new UsuarioRequest();
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioUseCase.editarUsuario(id, request)).thenReturn(usuario);

        ResponseEntity<UsuarioEntity> response = usuarioController.editarUsuario(id, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioUseCase).editarUsuario(id, request);
    }

    @Test
    void removerUsuario_deveRetornarNoContent() {
        Long id = 1L;

        ResponseEntity<Void> response = usuarioController.removerUsuario(id);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(usuarioUseCase).removerUsuario(id);
    }
}
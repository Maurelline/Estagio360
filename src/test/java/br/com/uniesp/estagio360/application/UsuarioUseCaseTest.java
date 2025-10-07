package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.enums.TipoUsuarioEnum;
import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.EspecialidadeEntity;
import br.com.uniesp.estagio360.domain.model.PeriodoAcademicoEntity;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import br.com.uniesp.estagio360.domain.ports.UsuarioRepository;
import br.com.uniesp.estagio360.domain.request.UsuarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarUsuario_deveCriarUsuarioAlunoComPeriodoAcademico() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Aluno");
        request.setPerfil(TipoUsuarioEnum.ALUNO);
        request.setIdPeriodoAcademico("P1");

        PeriodoAcademicoEntity periodo = new PeriodoAcademicoEntity();
        when(periodoAcademicoRepository.findById("P1")).thenReturn(Optional.of(periodo));

        UsuarioEntity usuarioSalvo = new UsuarioEntity();
        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);

        UsuarioEntity result = usuarioUseCase.criarUsuario(request);

        assertEquals(usuarioSalvo, result);
        verify(periodoAcademicoRepository).findById("P1");
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void criarUsuario_deveLancarNotFoundExceptionQuandoPeriodoAcademicoNaoExiste() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Aluno");
        request.setPerfil(TipoUsuarioEnum.ALUNO);
        request.setIdPeriodoAcademico("P1");

        when(periodoAcademicoRepository.findById("P1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioUseCase.criarUsuario(request));
    }

    @Test
    void criarUsuario_deveCriarUsuarioPreceptorComEspecialidade() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Preceptor");
        request.setPerfil(TipoUsuarioEnum.PRECEPTOR);
        request.setIdEspecialidadePrincipal(2L);

        EspecialidadeEntity especialidade = new EspecialidadeEntity();
        when(especialidadeRepository.findById(2L)).thenReturn(Optional.of(especialidade));

        UsuarioEntity usuarioSalvo = new UsuarioEntity();
        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);

        UsuarioEntity result = usuarioUseCase.criarUsuario(request);

        assertEquals(usuarioSalvo, result);
        verify(especialidadeRepository).findById(2L);
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void criarUsuario_deveLancarNotFoundExceptionQuandoEspecialidadeNaoExiste() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Preceptor");
        request.setPerfil(TipoUsuarioEnum.PRECEPTOR);
        request.setIdEspecialidadePrincipal(2L);

        when(especialidadeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioUseCase.criarUsuario(request));
    }

    @Test
    void buscarUsuarioPorId_deveRetornarUsuarioQuandoExiste() {
        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<UsuarioEntity> result = usuarioUseCase.buscarUsuarioPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
    }

    @Test
    void listarUsuarios_deveRetornarListaDeUsuarios() {
        List<UsuarioEntity> usuarios = Arrays.asList(new UsuarioEntity(), new UsuarioEntity());
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> result = usuarioUseCase.listarUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void editarUsuario_deveEditarUsuarioComSucesso() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Antigo");
        usuario.setPerfil(TipoUsuarioEnum.ALUNO);

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Novo");
        request.setPerfil(TipoUsuarioEnum.ALUNO);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioEntity result = usuarioUseCase.editarUsuario(1L, request);

        assertEquals("Novo", result.getNome());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void editarUsuario_deveLancarNotFoundExceptionQuandoUsuarioNaoExiste() {
        UsuarioRequest request = new UsuarioRequest();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioUseCase.editarUsuario(1L, request));
    }

    @Test
    void removerUsuario_deveRemoverUsuarioQuandoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioUseCase.removerUsuario(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void removerUsuario_deveLancarNotFoundExceptionQuandoUsuarioNaoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> usuarioUseCase.removerUsuario(1L));
    }
}
package br.com.uniesp.estagio360.application;

import br.com.uniesp.estagio360.domain.enums.TipoUsuarioEnum;
import br.com.uniesp.estagio360.domain.exceptions.NotFoundException;
import br.com.uniesp.estagio360.domain.model.UsuarioEntity;
import br.com.uniesp.estagio360.domain.ports.EspecialidadeRepository;
import br.com.uniesp.estagio360.domain.ports.PeriodoAcademicoRepository;
import br.com.uniesp.estagio360.domain.ports.UsuarioRepository;
import br.com.uniesp.estagio360.domain.request.UsuarioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PeriodoAcademicoRepository periodoAcademicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public UsuarioEntity criarUsuario(UsuarioRequest request) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(request.getNome());
        usuario.setPerfil(request.getPerfil());

        if (request.getPerfil() == TipoUsuarioEnum.ALUNO && request.getIdPeriodoAcademico() != null) {
            usuario.setPeriodoAcademico(
                    periodoAcademicoRepository.findById(request.getIdPeriodoAcademico())
                            .orElseThrow(() -> new NotFoundException("Periodo Academico", request.getIdPeriodoAcademico()))
            );
        }

        if (request.getPerfil() == TipoUsuarioEnum.PRECEPTOR && request.getIdEspecialidadePrincipal() != null) {
            usuario.setEspecialidadePrincipal(
                    especialidadeRepository.findById(request.getIdEspecialidadePrincipal())
                            .orElseThrow(() -> new NotFoundException("Especialidade", request.getIdEspecialidadePrincipal().toString()))
            );
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioEntity> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<UsuarioEntity> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity editarUsuario(Long id, UsuarioRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário", id.toString()));

        if (request.getNome() != null) {
            usuario.setNome(request.getNome());
        }
        if (request.getPerfil() != null) {
            usuario.setPerfil(request.getPerfil());
        }
        if (request.getPerfil() == TipoUsuarioEnum.ALUNO && request.getIdPeriodoAcademico() != null) {
            usuario.setPeriodoAcademico(
                    periodoAcademicoRepository.findById(request.getIdPeriodoAcademico())
                            .orElseThrow(() -> new NotFoundException("Periodo Academico", request.getIdPeriodoAcademico()))
            );
        }
        if (request.getPerfil() == TipoUsuarioEnum.PRECEPTOR && request.getIdEspecialidadePrincipal() != null) {
            usuario.setEspecialidadePrincipal(
                    especialidadeRepository.findById(request.getIdEspecialidadePrincipal())
                            .orElseThrow(() -> new NotFoundException("Especialidade", request.getIdEspecialidadePrincipal().toString()))
            );
        }
        return usuarioRepository.save(usuario);
    }

    public void removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário", id.toString());
        }
        usuarioRepository.deleteById(id);
    }
}
package br.com.uniesp.estagio360.domain.request;

import br.com.uniesp.estagio360.domain.enums.TipoUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private TipoUsuarioEnum perfil;
    private String idPeriodoAcademico;
    private Long idEspecialidadePrincipal;
}
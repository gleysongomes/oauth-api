package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissaoUsuarioDTO {

	@EqualsAndHashCode.Include
	private Long cdPermissao;

	@EqualsAndHashCode.Include
	private Long cdUsuario;

	private String nmUsuarioCriacao;

	private String nmUsuarioAtualizacao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Boolean flAtiva;
}

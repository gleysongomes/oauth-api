package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioDTO {

	@EqualsAndHashCode.Include
	private Long cdUsuario;

	private String login;

	private String email;

	private String nome;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Long cdUsuarioCriacao;

	private Long cdUsuarioAtualizacao;

	private Boolean flAtivo;
}

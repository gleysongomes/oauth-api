package io.github.gleysongomes.oauth.dto.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioMapping {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_USUARIO")
	private Long cdUsuario;

	private String login;

	private String email;

	private String nome;

	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "CD_USUARIO_CRIACAO")
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "FL_ATIVO")
	private Boolean flAtivo;
}

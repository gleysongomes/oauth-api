package io.github.gleysongomes.oauth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.github.gleysongomes.oauth.model.primarykey.GrupoUsuarioId;
import io.github.gleysongomes.oauth.model.resumo.UsuarioResumo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_GRUPO_USUARIO", schema = "OAUTH")
@IdClass(GrupoUsuarioId.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GrupoUsuario {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_GRUPO", nullable = false)
	private Long cdGrupo;

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_USUARIO", nullable = false)
	private Long cdUsuario;

	@Column(name = "CD_USUARIO_CRIACAO", nullable = false)
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "DT_CADASTRO", nullable = false)
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "FL_ATIVO", nullable = false)
	private Boolean flAtivo;

	@ManyToOne
	@JoinColumn(name = "CD_GRUPO", insertable = false, updatable = false)
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO", insertable = false, updatable = false)
	private UsuarioResumo usuario;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_CRIACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioCriacao;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_ATUALIZACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioAtualizacao;

}

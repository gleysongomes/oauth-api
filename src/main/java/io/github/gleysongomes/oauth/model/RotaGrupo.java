package io.github.gleysongomes.oauth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.github.gleysongomes.oauth.model.primarykey.RotaGrupoId;
import io.github.gleysongomes.oauth.model.resumo.UsuarioResumo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_ROTA_GRUPO", schema = "OAUTH")
@IdClass(RotaGrupoId.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RotaGrupo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_ROTA", nullable = false)
	private Long cdRota;

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_GRUPO", nullable = false)
	private Long cdGrupo;

	@Column(name = "CD_USUARIO_CRIACAO", nullable = false)
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "DT_CADASTRO", nullable = false)
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "FL_ATIVA", nullable = false)
	private Boolean flAtiva;

	@ManyToOne
	@JoinColumn(name = "CD_ROTA", insertable = false, updatable = false)
	private Rota rota;

	@ManyToOne
	@JoinColumn(name = "CD_GRUPO", insertable = false, updatable = false)
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_CRIACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioCriacao;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_ATUALIZACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioAtualizacao;

}

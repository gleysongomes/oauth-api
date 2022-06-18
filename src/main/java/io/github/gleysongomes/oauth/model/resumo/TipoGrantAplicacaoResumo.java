package io.github.gleysongomes.oauth.model.resumo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.github.gleysongomes.oauth.model.primarykey.TipoGrantAplicacaoId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_TIPO_GRANT_APLICACAO", schema = "OAUTH")
@IdClass(TipoGrantAplicacaoId.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoGrantAplicacaoResumo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_TIPO_GRANT", nullable = false)
	private Long cdTipoGrant;

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_APLICACAO", nullable = false)
	private Long cdAplicacao;

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
	@JoinColumn(name = "CD_TIPO_GRANT", insertable = false, updatable = false)
	private TipoGrantResumo tipoGrant;

	@ManyToOne
	@JoinColumn(name = "CD_APLICACAO", insertable = false, updatable = false)
	private AplicacaoResumo aplicacao;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_CRIACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioCriacao;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_ATUALIZACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioAtualizacao;

}

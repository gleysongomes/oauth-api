package io.github.gleysongomes.oauth.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.github.gleysongomes.oauth.model.resumo.TipoGrantAplicacaoResumo;
import io.github.gleysongomes.oauth.model.resumo.UsuarioResumo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_APLICACAO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aplicacao {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_APLICACAO")
	@SequenceGenerator(name = "SQ_APLICACAO", sequenceName = "OAUTH.SQ_APLICACAO", allocationSize = 1)
	@Column(name = "CD_APLICACAO")
	private Long cdAplicacao;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false)
	private String descricao;

	@Column(name = "DT_CADASTRO", nullable = false)
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "CD_USUARIO_CRIACAO", nullable = false)
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "FL_ATIVA", nullable = false)
	private Boolean flAtiva;

	@Enumerated(EnumType.STRING)
	@Column(name = "TP_APLICACAO", nullable = false)
	private TipoAplicacao tipo;

	@Column(nullable = false)
	private String segredo;

	@Column(name = "FL_SEGURANCA", nullable = false)
	private Boolean flSeguranca;

	@Column(name = "TMP_ACCESS_TOKEN", nullable = false)
	private Integer tmpAccessToken;

	@Column(name = "TMP_REFRESH_TOKEN")
	private Integer tmpRefreshToken;

	@Column(name = "FL_REFRESH_TOKEN", nullable = false)
	private Boolean flRefreshToken;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_CRIACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioCriacao;

	@ManyToOne
	@JoinColumn(name = "CD_USUARIO_ATUALIZACAO", insertable = false, updatable = false)
	private UsuarioResumo usuarioAtualizacao;

	@OneToMany(mappedBy = "cdAplicacao", fetch = FetchType.EAGER)
	private Set<TipoGrantAplicacaoResumo> tiposGrant = new HashSet<>();

	@OneToMany(mappedBy = "cdAplicacao", fetch = FetchType.EAGER)
	private Set<Escopo> escopos = new HashSet<>();

	@OneToMany(mappedBy = "cdAplicacao", fetch = FetchType.EAGER)
	private Set<Recurso> recursos = new HashSet<>();

	@OneToMany(mappedBy = "cdAplicacao", fetch = FetchType.EAGER)
	private Set<Autoridade> autoridades = new HashSet<>();

	@OneToMany(mappedBy = "cdAplicacao", fetch = FetchType.EAGER)
	private Set<Redirecionamento> redirecionamentos = new HashSet<>();
}

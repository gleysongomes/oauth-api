package io.github.gleysongomes.oauth.dto.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import io.github.gleysongomes.oauth.model.TipoAplicacao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_APLICACAO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AplicacaoMapping {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_APLICACAO")
	private Long cdAplicacao;

	private String nome;

	private String descricao;

	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "CD_USUARIO_CRIACAO")
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "NM_USUARIO_CRIACAO")
	private String nmUsuarioCriacao;

	@Column(name = "NM_USUARIO_ATUALIZACAO")
	private String nmUsuarioAtualizacao;

	@Column(name = "FL_ATIVA")
	private Boolean flAtiva;

	@Enumerated(EnumType.STRING)
	@Column(name = "TP_APLICACAO")
	private TipoAplicacao tipo;

	private String segredo;

	@Column(name = "FL_SEGURANCA")
	private Boolean flSeguranca;

	@Column(name = "TMP_ACCESS_TOKEN")
	private Integer tmpAccessToken;

	@Column(name = "TMP_REFRESH_TOKEN")
	private Integer tmpRefreshToken;

	@Column(name = "FL_REFRESH_TOKEN")
	private Boolean flRefreshToken;
}

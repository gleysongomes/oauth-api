package io.github.gleysongomes.oauth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_ROTA", schema = "OAUTH", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "NOME", "CD_APLICACAO" }, name = "UK_NCA_TB_ROTA") })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rota {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ROTA")
	@SequenceGenerator(name = "SQ_ROTA", sequenceName = "OAUTH.SQ_ROTA", allocationSize = 1)
	@Column(name = "CD_ROTA")
	private Long cdRota;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descricao;

	@Column(name = "DT_CADASTRO", nullable = false)
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "CD_APLICACAO", nullable = false)
	private Long cdAplicacao;

	@Column(name = "CD_USUARIO_CRIACAO", nullable = false)
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "FL_ATIVA", nullable = false)
	private Boolean flAtiva;

	@ManyToOne
	@JoinColumn(name = "CD_APLICACAO", insertable = false, updatable = false)
	private Aplicacao aplicacao;
}

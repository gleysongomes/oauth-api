package io.github.gleysongomes.oauth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_TIPO_GRANT", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoGrant {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TIPO_GRANT")
	@SequenceGenerator(name = "SQ_TIPO_GRANT", sequenceName = "OAUTH.SQ_TIPO_GRANT", allocationSize = 1)
	@Column(name = "CD_TIPO_GRANT")
	private Long cdTipoGrant;

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

	@Column(name = "FL_ATIVO", nullable = false)
	private Boolean flAtivo;
}

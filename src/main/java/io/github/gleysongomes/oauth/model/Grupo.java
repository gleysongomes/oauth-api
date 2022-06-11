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
@Table(name = "TB_GRUPO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_GRUPO")
	@SequenceGenerator(name = "SQ_GRUPO", sequenceName = "OAUTH.SQ_GRUPO", allocationSize = 1)
	@Column(name = "CD_GRUPO")
	private Long cdGrupo;

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

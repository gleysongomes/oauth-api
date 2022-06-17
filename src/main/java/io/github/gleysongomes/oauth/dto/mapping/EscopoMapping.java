package io.github.gleysongomes.oauth.dto.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_ESCOPO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EscopoMapping {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_ESCOPO")
	private Long cdEscopo;

	private String nome;

	private String descricao;

	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "NM_USUARIO_CRIACAO")
	private String nmUsuarioCriacao;

	@Column(name = "NM_USUARIO_ATUALIZACAO")
	private String nmUsuarioAtualizacao;

	@Column(name = "FL_ATIVO")
	private Boolean flAtivo;
}

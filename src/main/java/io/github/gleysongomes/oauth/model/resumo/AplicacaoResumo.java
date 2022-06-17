package io.github.gleysongomes.oauth.model.resumo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_APLICACAO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AplicacaoResumo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_APLICACAO")
	private Long cdAplicacao;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(name = "FL_ATIVA", nullable = false)
	private Boolean flAtiva;
}

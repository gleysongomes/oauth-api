package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AutoridadeDTO {

	@EqualsAndHashCode.Include
	private Long cdAutoridade;

	private String nome;

	private String descricao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Long cdAplicacao;

	private String nmAplicacao;

	private Boolean flAtiva;
}

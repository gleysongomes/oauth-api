package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RotaInput {

	@NotBlank
	private String nome;

	@NotBlank
	private String descricao;

	@NotNull
	private Long cdAplicacao;

	@NotNull
	private Boolean flAtiva;

}

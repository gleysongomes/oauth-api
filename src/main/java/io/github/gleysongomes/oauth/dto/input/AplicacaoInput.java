package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AplicacaoInput {

	@NotBlank
	private String nome;

	@NotBlank
	private String descricao;

	@NotNull
	private Boolean flAtiva;

	@NotBlank
	private String tipo;

	@NotBlank
	private String segredo;

	@NotNull
	private Boolean flSeguranca;

	@NotNull
	private Integer tmpAccessToken;

	private Integer tmpRefreshToken;

	@NotNull
	private Boolean flRefreshToken;

}

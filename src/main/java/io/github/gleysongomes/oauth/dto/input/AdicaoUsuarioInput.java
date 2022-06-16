package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdicaoUsuarioInput {

	@NotBlank
	private String login;

	@NotBlank
	private String email;

	@NotBlank
	private String nome;

	@NotBlank
	private String senha;

	@NotBlank
	private String senhaConfirmada;

	@NotNull
	private Boolean flAtivo;
}

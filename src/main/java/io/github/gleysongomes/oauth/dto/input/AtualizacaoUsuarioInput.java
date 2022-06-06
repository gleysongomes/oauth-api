package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoUsuarioInput {

	@NotBlank
	private String login;

	@NotBlank
	private String email;

	@NotBlank
	private String nome;

	private String senhaAtual;

	private String novaSenha;

	private String novaSenhaConfirmada;

	@NotNull
	private Boolean flAtivo;
}

package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoGrantInput {

	@NotBlank
	private String nome;

	private String descricao;

	@NotNull
	private Boolean flAtivo;

}

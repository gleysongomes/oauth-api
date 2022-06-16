package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoGrantAplicacaoInput {

	@NotNull
	private Long cdTipoGrant;

	@NotNull
	private Long cdAplicacao;

	@NotNull
	private Boolean flAtivo;
}

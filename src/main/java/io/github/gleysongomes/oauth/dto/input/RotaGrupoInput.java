package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RotaGrupoInput {

	@NotNull
	private Long cdRota;

	@NotNull
	private Long cdGrupo;

	@NotNull
	private Boolean flAtiva;
}

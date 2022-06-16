package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RotaUsuarioInput {

	@NotNull
	private Long cdRota;

	@NotNull
	private Long cdUsuario;

	@NotNull
	private Boolean flAtiva;
}

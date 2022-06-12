package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoUsuarioInput {

	@NotNull
	private Long cdGrupo;

	@NotNull
	private Long cdUsuario;

	@NotNull
	private Boolean flAtivo;
}

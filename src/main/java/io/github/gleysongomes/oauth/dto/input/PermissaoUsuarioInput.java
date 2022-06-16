package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoUsuarioInput {

	@NotNull
	private Long cdPermissao;

	@NotNull
	private Long cdUsuario;

	@NotNull
	private Boolean flAtiva;
}

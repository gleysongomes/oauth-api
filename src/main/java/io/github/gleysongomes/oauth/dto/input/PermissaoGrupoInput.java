package io.github.gleysongomes.oauth.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoGrupoInput {

	@NotNull
	private Long cdPermissao;

	@NotNull
	private Long cdGrupo;

	@NotNull
	private Boolean flAtiva;
}

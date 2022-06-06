package io.github.gleysongomes.oauth.dto.resumo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioResumoDTO {

	@EqualsAndHashCode.Include
	private Long cdUsuario;

	private String login;
	
	private String email;

	private String nome;
}

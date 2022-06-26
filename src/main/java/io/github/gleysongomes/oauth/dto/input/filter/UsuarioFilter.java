package io.github.gleysongomes.oauth.dto.input.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter extends ReportFilter {

	private static final long serialVersionUID = -1905276950390101232L;

	private String login;

	private String email;

	private String nome;

	private Boolean flAtivo;
}

package io.github.gleysongomes.oauth.dto.input.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoFilter extends ReportFilter {

	private static final long serialVersionUID = 536085315597815782L;

	private String nome;

	private String descricao;

	private Boolean flAtiva;
}

package io.github.gleysongomes.oauth.dto.input.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoFilter extends ReportFilter {

	private static final long serialVersionUID = -2866301980426793907L;

	private String nome;

	private String descricao;

	private Boolean flAtivo;
}

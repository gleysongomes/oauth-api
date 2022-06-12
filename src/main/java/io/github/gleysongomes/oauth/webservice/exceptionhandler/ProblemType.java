package io.github.gleysongomes.oauth.webservice.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	// @formatter:off
	DADOS_INVALIDOS("Dados inválidos"),
	NAO_ENCONTRADO("Recurso não encontrado"),
	ERRO_NEGOCIO("Erro de negócio"),
	ERRO_DE_INTEGRIDADE("Erro de integridade dos dados"),
	ERRO_DE_SISTEMA("Erro de sistema");
	// @formatter:on

	private String title;

	ProblemType(String title) {
		this.title = title;
	}

}

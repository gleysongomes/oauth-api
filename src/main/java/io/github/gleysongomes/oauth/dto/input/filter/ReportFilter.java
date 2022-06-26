package io.github.gleysongomes.oauth.dto.input.filter;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportFilter implements Serializable {

	private static final long serialVersionUID = 1237360769131947943L;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtCadastroInicial;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtCadastroFinal;

	private String tipoRelatorio;

	private String tipoArquivo;
}

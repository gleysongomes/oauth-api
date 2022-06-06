package io.github.gleysongomes.oauth.dto.input.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFilter {

	private String login;

	private String email;

	private String nome;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtCadastroInicial;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtCadastroFinal;

	private Boolean flAtivo;
}

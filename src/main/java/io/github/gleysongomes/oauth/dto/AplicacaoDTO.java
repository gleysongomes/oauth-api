package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import io.github.gleysongomes.oauth.model.TipoAplicacao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AplicacaoDTO {

	@EqualsAndHashCode.Include
	private Long cdAplicacao;

	private String nome;

	private String descricao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private String nmUsuarioCriacao;

	private String nmUsuarioAtualizacao;

	private Boolean flAtiva;

	private TipoAplicacao tipo;

	private String segredo;

	private Boolean flSeguranca;

	private Integer tmpAccessToken;

	private Integer tmpRefreshToken;

	private Boolean flRefreshToken;
}

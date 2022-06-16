package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoGrantAplicacaoDTO {

	@EqualsAndHashCode.Include
	private Long cdTipoGrant;

	@EqualsAndHashCode.Include
	private Long cdAplicacao;

	private String nmUsuarioCriacao;

	private String nmUsuarioAtualizacao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Boolean flAtivo;
}

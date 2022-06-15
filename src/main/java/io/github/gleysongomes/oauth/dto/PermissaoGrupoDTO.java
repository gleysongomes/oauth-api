package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissaoGrupoDTO {

	@EqualsAndHashCode.Include
	private Long cdPermissao;

	@EqualsAndHashCode.Include
	private Long cdGrupo;

	private String nmUsuarioCriacao;

	private String nmUsuarioAtualizacao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Boolean flAtiva;
}

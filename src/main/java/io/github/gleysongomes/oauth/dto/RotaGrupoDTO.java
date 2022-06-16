package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RotaGrupoDTO {

	@EqualsAndHashCode.Include
	private Long cdRota;

	@EqualsAndHashCode.Include
	private Long cdGrupo;

	private String nmUsuarioCriacao;

	private String nmUsuarioAtualizacao;

	private Date dtCadastro;

	private Date dtAtualizacao;

	private Boolean flAtiva;
}

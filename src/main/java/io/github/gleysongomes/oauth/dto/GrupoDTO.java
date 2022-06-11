package io.github.gleysongomes.oauth.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GrupoDTO {

	@EqualsAndHashCode.Include
	private Long cdGrupo;
	
	private String nome;
	
	private String descricao;
	
	private Date dtCadastro;
	
	private Date dtAtualizacao;
	
	private Long cdUsuarioCriacao;
	
	private Long cdUsuarioAtualizacao;
	
	private Boolean flAtivo;
}

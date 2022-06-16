package io.github.gleysongomes.oauth.dto.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_TIPO_GRANT", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoGrantMapping {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_TIPO_GRANT")
	private Long cdTipoGrant;

	private String nome;

	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "CD_USUARIO_CRIACAO")
	private Long cdUsuarioCriacao;

	@Column(name = "CD_USUARIO_ATUALIZACAO")
	private Long cdUsuarioAtualizacao;

	@Column(name = "NM_USUARIO_CRIACAO")
	private String nmUsuarioCriacao;

	@Column(name = "NM_USUARIO_ATUALIZACAO")
	private String nmUsuarioAtualizacao;

	@Column(name = "FL_ATIVO")
	private Boolean flAtivo;
}

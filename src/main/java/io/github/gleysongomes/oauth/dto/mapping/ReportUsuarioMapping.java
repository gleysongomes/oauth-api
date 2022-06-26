package io.github.gleysongomes.oauth.dto.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @formatter:off
@SqlResultSetMappings({ @SqlResultSetMapping(name = "ReportUsuarioMap", classes = { 
		@ConstructorResult(targetClass = ReportUsuarioMapping.class, columns = { 
				@ColumnResult(name = "CD_USUARIO", type = Long.class), 
				@ColumnResult(name = "LOGIN", type = String.class), 
				@ColumnResult(name = "EMAIL", type = String.class), 
				@ColumnResult(name = "NOME", type = String.class), 
				@ColumnResult(name = "DT_CADASTRO", type = Date.class), 
				@ColumnResult(name = "DT_ATUALIZACAO", type = Date.class), 
				@ColumnResult(name = "FL_ATIVO", type = Boolean.class), 
				@ColumnResult(name = "NM_USUARIO_CRIACAO", type = String.class), 
				@ColumnResult(name = "NM_USUARIO_ATUALIZACAO", type = String.class)
		})
		})
})
// @formatter:on
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReportUsuarioMapping {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_USUARIO")
	private Long cdUsuario;

	private String login;

	private String email;

	private String nome;

	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	@Column(name = "DT_ATUALIZACAO")
	private Date dtAtualizacao;

	@Column(name = "FL_ATIVO")
	private Boolean flAtivo;

	@Column(name = "NM_USUARIO_CRIACAO")
	private String nmUsuarioCriacao;

	@Column(name = "NM_USUARIO_ATUALIZACAO")
	private String nmUsuarioAtualizacao;
}

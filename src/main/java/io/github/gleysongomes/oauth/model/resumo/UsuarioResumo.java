package io.github.gleysongomes.oauth.model.resumo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_USUARIO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioResumo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_USUARIO")
	private Long cdUsuario;

	private String login;

	private String email;

	private String nome;
}

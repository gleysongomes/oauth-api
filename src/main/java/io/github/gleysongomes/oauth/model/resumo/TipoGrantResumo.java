package io.github.gleysongomes.oauth.model.resumo;

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
public class TipoGrantResumo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_TIPO_GRANT")
	private Long cdTipoGrant;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(name = "FL_ATIVO", nullable = false)
	private Boolean flAtivo;
}

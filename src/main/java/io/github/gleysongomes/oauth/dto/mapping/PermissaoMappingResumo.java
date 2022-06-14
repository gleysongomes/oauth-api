package io.github.gleysongomes.oauth.dto.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "TB_PERMISSAO", schema = "OAUTH")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PermissaoMappingResumo {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "CD_PERMISSAO")
	private Long cdPermissao;

	private String nome;
}

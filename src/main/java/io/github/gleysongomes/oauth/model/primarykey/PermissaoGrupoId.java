package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoGrupoId implements Serializable {

	private static final long serialVersionUID = -3544506481970855689L;

	private Long cdPermissao;

	private Long cdGrupo;
}

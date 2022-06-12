package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoUsuarioId implements Serializable {

	private static final long serialVersionUID = -1013358229498862513L;

	private Long cdGrupo;

	private Long cdUsuario;
}

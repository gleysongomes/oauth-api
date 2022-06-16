package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotaGrupoId implements Serializable {

	private static final long serialVersionUID = -4035358863770708869L;

	private Long cdRota;

	private Long cdGrupo;
}

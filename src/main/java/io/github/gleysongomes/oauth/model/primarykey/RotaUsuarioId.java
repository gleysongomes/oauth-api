package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotaUsuarioId implements Serializable {

	private static final long serialVersionUID = -7548825057246526784L;

	private Long cdRota;

	private Long cdUsuario;
}

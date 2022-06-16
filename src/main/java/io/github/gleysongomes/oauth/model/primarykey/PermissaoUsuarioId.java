package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoUsuarioId implements Serializable {

	private static final long serialVersionUID = 5187759289311333382L;

	private Long cdPermissao;

	private Long cdUsuario;
}

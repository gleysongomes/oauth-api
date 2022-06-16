package io.github.gleysongomes.oauth.model.primarykey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoGrantAplicacaoId implements Serializable {

	private static final long serialVersionUID = 6381262452922145439L;

	private Long cdTipoGrant;

	private Long cdAplicacao;
}

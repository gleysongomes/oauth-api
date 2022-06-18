package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RedirecionamentoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RedirecionamentoMapping;
import io.github.gleysongomes.oauth.model.Redirecionamento;

public interface RedirecionamentoService {

	Redirecionamento adicionar(Redirecionamento redirecionamento);

	Redirecionamento atualizar(Redirecionamento redirecionamento);

	PageDTO<RedirecionamentoMapping> listar(RedirecionamentoFilter redirecionamentoFilter,
			PageDTO<RedirecionamentoMapping> pageDTO);

	Redirecionamento buscar(Long cdRedirecionamento);
}

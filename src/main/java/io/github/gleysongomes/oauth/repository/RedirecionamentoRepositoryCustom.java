package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RedirecionamentoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RedirecionamentoMapping;

public interface RedirecionamentoRepositoryCustom {

	List<RedirecionamentoMapping> listar(RedirecionamentoFilter redirecionamentoFilter,
			PageDTO<RedirecionamentoMapping> pageDTO);

	Long contar(RedirecionamentoFilter redirecionamentoFilter);
}

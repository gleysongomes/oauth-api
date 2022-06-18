package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AutoridadeFilter;
import io.github.gleysongomes.oauth.dto.mapping.AutoridadeMapping;

public interface AutoridadeRepositoryCustom {

	List<AutoridadeMapping> listar(AutoridadeFilter autoridadeFilter, PageDTO<AutoridadeMapping> pageDTO);

	Long contar(AutoridadeFilter autoridadeFilter);
}

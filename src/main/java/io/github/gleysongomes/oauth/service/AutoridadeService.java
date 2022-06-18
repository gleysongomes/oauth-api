package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AutoridadeFilter;
import io.github.gleysongomes.oauth.dto.mapping.AutoridadeMapping;
import io.github.gleysongomes.oauth.model.Autoridade;

public interface AutoridadeService {

	Autoridade adicionar(Autoridade autoridade);

	Autoridade atualizar(Autoridade autoridade);

	PageDTO<AutoridadeMapping> listar(AutoridadeFilter autoridadeFilter, PageDTO<AutoridadeMapping> pageDTO);

	Autoridade buscar(Long cdAutoridade);
}

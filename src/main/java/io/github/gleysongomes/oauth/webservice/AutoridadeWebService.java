package io.github.gleysongomes.oauth.webservice;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.gleysongomes.oauth.dto.AutoridadeDTO;
import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.AutoridadeInput;
import io.github.gleysongomes.oauth.dto.input.filter.AutoridadeFilter;
import io.github.gleysongomes.oauth.dto.mapping.AutoridadeMapping;
import io.github.gleysongomes.oauth.mapper.AutoridadeMapper;
import io.github.gleysongomes.oauth.model.Autoridade;
import io.github.gleysongomes.oauth.service.AutoridadeService;

@RestController
@RequestMapping(path = "/autoridades", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutoridadeWebService {

	private final AutoridadeService autoridadeService;

	private final AutoridadeMapper autoridadeMapper;

	public AutoridadeWebService(AutoridadeService autoridadeService, AutoridadeMapper autoridadeMapper) {
		this.autoridadeService = autoridadeService;
		this.autoridadeMapper = autoridadeMapper;
	}

	@GetMapping
	public PageDTO<AutoridadeMapping> listar(AutoridadeFilter autoridadeFilter, @Valid PageDTO<AutoridadeMapping> pageDTO) {
		PageDTO<AutoridadeMapping> autoridades = autoridadeService.listar(autoridadeFilter, pageDTO);

		return autoridades;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AutoridadeDTO adicionar(@RequestBody @Valid AutoridadeInput gupoInput) {
		Autoridade autoridade = autoridadeMapper.toDomainObject(gupoInput);

		autoridade = autoridadeService.adicionar(autoridade);

		AutoridadeDTO autoridadeDTO = autoridadeMapper.toDto(autoridade);

		return autoridadeDTO;
	}

	@GetMapping("/{cdAutoridade}")
	public AutoridadeDTO buscar(@PathVariable Long cdAutoridade) {
		Autoridade autoridade = autoridadeService.buscar(cdAutoridade);

		AutoridadeDTO autoridadeDTO = autoridadeMapper.toDto(autoridade);

		return autoridadeDTO;
	}

	@PutMapping("/{cdAutoridade}")
	public AutoridadeDTO atualizar(@PathVariable Long cdAutoridade, @RequestBody @Valid AutoridadeInput autoridadeInput) {
		Autoridade autoridade = autoridadeService.buscar(cdAutoridade);

		autoridadeMapper.copyToDomainObject(autoridadeInput, autoridade);

		autoridade = autoridadeService.atualizar(autoridade);

		AutoridadeDTO autoridadeDTO = autoridadeMapper.toDto(autoridade);

		return autoridadeDTO;
	}

}

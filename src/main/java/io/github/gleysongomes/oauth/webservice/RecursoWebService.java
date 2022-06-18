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

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.RecursoDTO;
import io.github.gleysongomes.oauth.dto.input.RecursoInput;
import io.github.gleysongomes.oauth.dto.input.filter.RecursoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RecursoMapping;
import io.github.gleysongomes.oauth.mapper.RecursoMapper;
import io.github.gleysongomes.oauth.model.Recurso;
import io.github.gleysongomes.oauth.service.RecursoService;

@RestController
@RequestMapping(path = "/recursos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecursoWebService {

	private final RecursoService recursoService;

	private final RecursoMapper recursoMapper;

	public RecursoWebService(RecursoService recursoService, RecursoMapper recursoMapper) {
		this.recursoService = recursoService;
		this.recursoMapper = recursoMapper;
	}

	@GetMapping
	public PageDTO<RecursoMapping> listar(RecursoFilter recursoFilter, @Valid PageDTO<RecursoMapping> pageDTO) {
		PageDTO<RecursoMapping> recursos = recursoService.listar(recursoFilter, pageDTO);

		return recursos;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RecursoDTO adicionar(@RequestBody @Valid RecursoInput gupoInput) {
		Recurso recurso = recursoMapper.toDomainObject(gupoInput);

		recurso = recursoService.adicionar(recurso);

		RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

		return recursoDTO;
	}

	@GetMapping("/{cdRecurso}")
	public RecursoDTO buscar(@PathVariable Long cdRecurso) {
		Recurso recurso = recursoService.buscar(cdRecurso);

		RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

		return recursoDTO;
	}

	@PutMapping("/{cdRecurso}")
	public RecursoDTO atualizar(@PathVariable Long cdRecurso, @RequestBody @Valid RecursoInput recursoInput) {
		Recurso recurso = recursoService.buscar(cdRecurso);

		recursoMapper.copyToDomainObject(recursoInput, recurso);

		recurso = recursoService.atualizar(recurso);

		RecursoDTO recursoDTO = recursoMapper.toDto(recurso);

		return recursoDTO;
	}

}

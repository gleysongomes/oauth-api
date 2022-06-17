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

import io.github.gleysongomes.oauth.dto.EscopoDTO;
import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.EscopoInput;
import io.github.gleysongomes.oauth.dto.input.filter.EscopoFilter;
import io.github.gleysongomes.oauth.dto.mapping.EscopoMapping;
import io.github.gleysongomes.oauth.mapper.EscopoMapper;
import io.github.gleysongomes.oauth.model.Escopo;
import io.github.gleysongomes.oauth.service.EscopoService;

@RestController
@RequestMapping(path = "/escopos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EscopoWebService {

	private final EscopoService escopoService;

	private final EscopoMapper escopoMapper;

	public EscopoWebService(EscopoService escopoService, EscopoMapper escopoMapper) {
		this.escopoService = escopoService;
		this.escopoMapper = escopoMapper;
	}

	@GetMapping
	public PageDTO<EscopoMapping> listar(EscopoFilter escopoFilter, @Valid PageDTO<EscopoMapping> pageDTO) {
		PageDTO<EscopoMapping> escopos = escopoService.listar(escopoFilter, pageDTO);

		return escopos;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EscopoDTO adicionar(@RequestBody @Valid EscopoInput gupoInput) {
		Escopo escopo = escopoMapper.toDomainObject(gupoInput);

		escopo = escopoService.adicionar(escopo);

		EscopoDTO escopoDTO = escopoMapper.toDto(escopo);

		return escopoDTO;
	}

	@GetMapping("/{cdEscopo}")
	public EscopoDTO buscar(@PathVariable Long cdEscopo) {
		Escopo escopo = escopoService.buscar(cdEscopo);

		EscopoDTO escopoDTO = escopoMapper.toDto(escopo);

		return escopoDTO;
	}

	@PutMapping("/{cdEscopo}")
	public EscopoDTO atualizar(@PathVariable Long cdEscopo, @RequestBody @Valid EscopoInput escopoInput) {
		Escopo escopo = escopoService.buscar(cdEscopo);

		escopoMapper.copyToDomainObject(escopoInput, escopo);

		escopo = escopoService.atualizar(escopo);

		EscopoDTO escopoDTO = escopoMapper.toDto(escopo);

		return escopoDTO;
	}

}

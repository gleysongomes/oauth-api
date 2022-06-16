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
import io.github.gleysongomes.oauth.dto.TipoGrantDTO;
import io.github.gleysongomes.oauth.dto.input.TipoGrantInput;
import io.github.gleysongomes.oauth.dto.input.filter.TipoGrantFilter;
import io.github.gleysongomes.oauth.dto.mapping.TipoGrantMapping;
import io.github.gleysongomes.oauth.mapper.TipoGrantMapper;
import io.github.gleysongomes.oauth.model.TipoGrant;
import io.github.gleysongomes.oauth.service.TipoGrantService;

@RestController
@RequestMapping(path = "/tipos-grants", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoGrantWebService {

	private final TipoGrantService tipoGrantService;

	private final TipoGrantMapper tipoGrantMapper;

	public TipoGrantWebService(TipoGrantService tipoGrantService, TipoGrantMapper tipoGrantMapper) {
		this.tipoGrantService = tipoGrantService;
		this.tipoGrantMapper = tipoGrantMapper;
	}

	@GetMapping
	public PageDTO<TipoGrantMapping> listar(TipoGrantFilter tipoGrantFilter, @Valid PageDTO<TipoGrantMapping> pageDTO) {
		PageDTO<TipoGrantMapping> tiposGrants = tipoGrantService.listar(tipoGrantFilter, pageDTO);

		return tiposGrants;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoGrantDTO adicionar(@RequestBody @Valid TipoGrantInput gupoInput) {
		TipoGrant tipoGrant = tipoGrantMapper.toDomainObject(gupoInput);

		tipoGrant = tipoGrantService.adicionar(tipoGrant);

		TipoGrantDTO tipoGrantDTO = tipoGrantMapper.toDto(tipoGrant);

		return tipoGrantDTO;
	}

	@GetMapping("/{cdTipoGrant}")
	public TipoGrantDTO buscar(@PathVariable Long cdTipoGrant) {
		TipoGrant tipoGrant = tipoGrantService.buscar(cdTipoGrant);

		TipoGrantDTO tipoGrantDTO = tipoGrantMapper.toDto(tipoGrant);

		return tipoGrantDTO;
	}

	@PutMapping("/{cdTipoGrant}")
	public TipoGrantDTO atualizar(@PathVariable Long cdTipoGrant, @RequestBody @Valid TipoGrantInput tipoGrantInput) {
		TipoGrant tipoGrant = tipoGrantService.buscar(cdTipoGrant);

		tipoGrantMapper.copyToDomainObject(tipoGrantInput, tipoGrant);

		tipoGrant = tipoGrantService.atualizar(tipoGrant);

		TipoGrantDTO tipoGrantDTO = tipoGrantMapper.toDto(tipoGrant);

		return tipoGrantDTO;
	}

}

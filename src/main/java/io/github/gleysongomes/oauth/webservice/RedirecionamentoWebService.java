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
import io.github.gleysongomes.oauth.dto.RedirecionamentoDTO;
import io.github.gleysongomes.oauth.dto.input.RedirecionamentoInput;
import io.github.gleysongomes.oauth.dto.input.filter.RedirecionamentoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RedirecionamentoMapping;
import io.github.gleysongomes.oauth.mapper.RedirecionamentoMapper;
import io.github.gleysongomes.oauth.model.Redirecionamento;
import io.github.gleysongomes.oauth.service.RedirecionamentoService;

@RestController
@RequestMapping(path = "/redirecionamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RedirecionamentoWebService {

	private final RedirecionamentoService redirecionamentoService;

	private final RedirecionamentoMapper redirecionamentoMapper;

	public RedirecionamentoWebService(RedirecionamentoService redirecionamentoService,
			RedirecionamentoMapper redirecionamentoMapper) {
		this.redirecionamentoService = redirecionamentoService;
		this.redirecionamentoMapper = redirecionamentoMapper;
	}

	@GetMapping
	public PageDTO<RedirecionamentoMapping> listar(RedirecionamentoFilter redirecionamentoFilter,
			@Valid PageDTO<RedirecionamentoMapping> pageDTO) {
		PageDTO<RedirecionamentoMapping> redirecionamentos = redirecionamentoService.listar(redirecionamentoFilter, pageDTO);

		return redirecionamentos;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RedirecionamentoDTO adicionar(@RequestBody @Valid RedirecionamentoInput gupoInput) {
		Redirecionamento redirecionamento = redirecionamentoMapper.toDomainObject(gupoInput);

		redirecionamento = redirecionamentoService.adicionar(redirecionamento);

		RedirecionamentoDTO redirecionamentoDTO = redirecionamentoMapper.toDto(redirecionamento);

		return redirecionamentoDTO;
	}

	@GetMapping("/{cdRedirecionamento}")
	public RedirecionamentoDTO buscar(@PathVariable Long cdRedirecionamento) {
		Redirecionamento redirecionamento = redirecionamentoService.buscar(cdRedirecionamento);

		RedirecionamentoDTO redirecionamentoDTO = redirecionamentoMapper.toDto(redirecionamento);

		return redirecionamentoDTO;
	}

	@PutMapping("/{cdRedirecionamento}")
	public RedirecionamentoDTO atualizar(@PathVariable Long cdRedirecionamento,
			@RequestBody @Valid RedirecionamentoInput redirecionamentoInput) {
		Redirecionamento redirecionamento = redirecionamentoService.buscar(cdRedirecionamento);

		redirecionamentoMapper.copyToDomainObject(redirecionamentoInput, redirecionamento);

		redirecionamento = redirecionamentoService.atualizar(redirecionamento);

		RedirecionamentoDTO redirecionamentoDTO = redirecionamentoMapper.toDto(redirecionamento);

		return redirecionamentoDTO;
	}

}

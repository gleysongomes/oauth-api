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
import io.github.gleysongomes.oauth.dto.RotaDTO;
import io.github.gleysongomes.oauth.dto.input.RotaInput;
import io.github.gleysongomes.oauth.dto.input.filter.RotaFilter;
import io.github.gleysongomes.oauth.dto.mapping.RotaMapping;
import io.github.gleysongomes.oauth.mapper.RotaMapper;
import io.github.gleysongomes.oauth.model.Rota;
import io.github.gleysongomes.oauth.service.RotaService;

@RestController
@RequestMapping(path = "/rotas", produces = MediaType.APPLICATION_JSON_VALUE)
public class RotaWebService {

	private final RotaService rotaService;

	private final RotaMapper rotaMapper;

	public RotaWebService(RotaService rotaService, RotaMapper rotaMapper) {
		this.rotaService = rotaService;
		this.rotaMapper = rotaMapper;
	}

	@GetMapping
	public PageDTO<RotaMapping> listar(RotaFilter rotaFilter, @Valid PageDTO<RotaMapping> paginacaoTO) {
		PageDTO<RotaMapping> rotas = rotaService.listar(rotaFilter, paginacaoTO);

		return rotas;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RotaDTO adicionar(@RequestBody @Valid RotaInput gupoInput) {
		Rota rota = rotaMapper.toDomainObject(gupoInput);

		rota = rotaService.adicionar(rota);

		RotaDTO rotaDTO = rotaMapper.toDto(rota);

		return rotaDTO;
	}

	@GetMapping("/{cdRota}")
	public RotaDTO buscar(@PathVariable Long cdRota) {
		Rota rota = rotaService.buscar(cdRota);

		RotaDTO rotaDTO = rotaMapper.toDto(rota);

		return rotaDTO;
	}

	@PutMapping("/{cdRota}")
	public RotaDTO atualizar(@PathVariable Long cdRota, @RequestBody @Valid RotaInput rotaInput) {
		Rota rota = rotaService.buscar(cdRota);

		rotaMapper.copyToDomainObject(rotaInput, rota);

		rota = rotaService.atualizar(rota);

		RotaDTO rotaDTO = rotaMapper.toDto(rota);

		return rotaDTO;
	}

}

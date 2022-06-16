package io.github.gleysongomes.oauth.webservice;

import java.util.List;

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

import io.github.gleysongomes.oauth.dto.RotaGrupoDTO;
import io.github.gleysongomes.oauth.dto.input.RotaGrupoInput;
import io.github.gleysongomes.oauth.mapper.RotaGrupoMapper;
import io.github.gleysongomes.oauth.model.RotaGrupo;
import io.github.gleysongomes.oauth.service.RotaGrupoService;

@RestController
@RequestMapping(path = "/rotas-grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RotaGrupoWebService {

	private final RotaGrupoService rotaGrupoService;

	private final RotaGrupoMapper rotaGrupoMapper;

	public RotaGrupoWebService(RotaGrupoService rotaGrupoService, RotaGrupoMapper rotaGrupoMapper) {
		this.rotaGrupoService = rotaGrupoService;
		this.rotaGrupoMapper = rotaGrupoMapper;
	}

	@GetMapping("/rotas/{cdGrupo}")
	public List<RotaGrupoDTO> findByCdGrupoAndFlAtivaIsTrue(@PathVariable Long cdGrupo) {
		List<RotaGrupo> rotas = rotaGrupoService.findByCdGrupoAndFlAtivaIsTrue(cdGrupo);

		List<RotaGrupoDTO> rotasDTOs = rotaGrupoMapper.toDtos(rotas);

		return rotasDTOs;
	}

	@GetMapping("/grupos/{cdRota}")
	public List<RotaGrupoDTO> findByCdRotaAndFlAtivaIsTrue(@PathVariable Long cdRota) {
		List<RotaGrupo> grupos = rotaGrupoService.findByCdRotaAndFlAtivaIsTrue(cdRota);

		List<RotaGrupoDTO> gruposDTOs = rotaGrupoMapper.toDtos(grupos);

		return gruposDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RotaGrupoDTO adicionar(@RequestBody @Valid RotaGrupoInput rotaGrupoInput) {
		RotaGrupo rotaGrupo = rotaGrupoMapper.toDomainObject(rotaGrupoInput);

		rotaGrupo = rotaGrupoService.adicionar(rotaGrupo);

		RotaGrupoDTO rotaGrupoDTO = rotaGrupoMapper.toDto(rotaGrupo);

		return rotaGrupoDTO;
	}

	@GetMapping("/{cdRota}/{cdGrupo}")
	public RotaGrupoDTO buscar(@PathVariable Long cdRota, @PathVariable Long cdGrupo) {
		RotaGrupo rotaGrupo = rotaGrupoService.buscar(cdRota, cdGrupo);

		RotaGrupoDTO rotaGrupoDTO = rotaGrupoMapper.toDto(rotaGrupo);

		return rotaGrupoDTO;
	}

	@PutMapping("/{cdRota}/{cdGrupo}")
	public RotaGrupoDTO atualizar(@PathVariable Long cdRota, @PathVariable Long cdGrupo,
			@RequestBody @Valid RotaGrupoInput rotaGrupoInput) {
		rotaGrupoService.validarAtualizacao(cdRota, cdGrupo, rotaGrupoInput);

		RotaGrupo rotaGrupo = rotaGrupoService.buscar(cdRota, cdGrupo);

		rotaGrupoMapper.copyToDomainObject(rotaGrupoInput, rotaGrupo);

		rotaGrupo = rotaGrupoService.atualizar(rotaGrupo);

		RotaGrupoDTO rotaGrupoDTO = rotaGrupoMapper.toDto(rotaGrupo);

		return rotaGrupoDTO;
	}

}

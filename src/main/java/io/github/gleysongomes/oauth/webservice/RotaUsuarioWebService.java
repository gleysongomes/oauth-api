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

import io.github.gleysongomes.oauth.dto.RotaUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.RotaUsuarioInput;
import io.github.gleysongomes.oauth.mapper.RotaUsuarioMapper;
import io.github.gleysongomes.oauth.model.RotaUsuario;
import io.github.gleysongomes.oauth.service.RotaUsuarioService;

@RestController
@RequestMapping(path = "/rotas-usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class RotaUsuarioWebService {

	private final RotaUsuarioService rotaUsuarioService;

	private final RotaUsuarioMapper rotaUsuarioMapper;

	public RotaUsuarioWebService(RotaUsuarioService rotaUsuarioService, RotaUsuarioMapper rotaUsuarioMapper) {
		this.rotaUsuarioService = rotaUsuarioService;
		this.rotaUsuarioMapper = rotaUsuarioMapper;
	}

	@GetMapping("/rotas/{cdUsuario}")
	public List<RotaUsuarioDTO> findByCdUsuarioAndFlAtivaIsTrue(@PathVariable Long cdUsuario) {
		List<RotaUsuario> rotas = rotaUsuarioService.findByCdUsuarioAndFlAtivaIsTrue(cdUsuario);

		List<RotaUsuarioDTO> rotasDTOs = rotaUsuarioMapper.toDtos(rotas);

		return rotasDTOs;
	}

	@GetMapping("/usuarios/{cdRota}")
	public List<RotaUsuarioDTO> findByCdRotaAndFlAtivaIsTrue(@PathVariable Long cdRota) {
		List<RotaUsuario> usuarios = rotaUsuarioService.findByCdRotaAndFlAtivaIsTrue(cdRota);

		List<RotaUsuarioDTO> usuariosDTOs = rotaUsuarioMapper.toDtos(usuarios);

		return usuariosDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RotaUsuarioDTO adicionar(@RequestBody @Valid RotaUsuarioInput rotaUsuarioInput) {
		RotaUsuario rotaUsuario = rotaUsuarioMapper.toDomainObject(rotaUsuarioInput);

		rotaUsuario = rotaUsuarioService.adicionar(rotaUsuario);

		RotaUsuarioDTO rotaUsuarioDTO = rotaUsuarioMapper.toDto(rotaUsuario);

		return rotaUsuarioDTO;
	}

	@GetMapping("/{cdRota}/{cdUsuario}")
	public RotaUsuarioDTO buscar(@PathVariable Long cdRota, @PathVariable Long cdUsuario) {
		RotaUsuario rotaUsuario = rotaUsuarioService.buscar(cdRota, cdUsuario);

		RotaUsuarioDTO rotaUsuarioDTO = rotaUsuarioMapper.toDto(rotaUsuario);

		return rotaUsuarioDTO;
	}

	@PutMapping("/{cdRota}/{cdUsuario}")
	public RotaUsuarioDTO atualizar(@PathVariable Long cdRota, @PathVariable Long cdUsuario,
			@RequestBody @Valid RotaUsuarioInput rotaUsuarioInput) {
		rotaUsuarioService.validarAtualizacao(cdRota, cdUsuario, rotaUsuarioInput);

		RotaUsuario rotaUsuario = rotaUsuarioService.buscar(cdRota, cdUsuario);

		rotaUsuarioMapper.copyToDomainObject(rotaUsuarioInput, rotaUsuario);

		rotaUsuario = rotaUsuarioService.atualizar(rotaUsuario);

		RotaUsuarioDTO rotaUsuarioDTO = rotaUsuarioMapper.toDto(rotaUsuario);

		return rotaUsuarioDTO;
	}

}

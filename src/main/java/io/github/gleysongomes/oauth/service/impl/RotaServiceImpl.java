package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RotaFilter;
import io.github.gleysongomes.oauth.dto.mapping.RotaMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Rota;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.RotaRepository;
import io.github.gleysongomes.oauth.service.RotaService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class RotaServiceImpl implements RotaService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RotaRepository rotaRepository;

	private final UsuarioService usuarioService;

	public RotaServiceImpl(RotaRepository rotaRepository, UsuarioService usuarioService) {
		this.rotaRepository = rotaRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public Rota adicionar(Rota rota) {
		validarRota(rota);

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		try {
			rota.setDtCadastro(new Date());
			rota.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			rota = rotaRepository.save(rota);
		} catch (Exception e) {
			log.debug("Erro ao adicionar rota: {}.", rota.toString());
			throw new ApiException("Erro ao adicionar rota.", e);
		}
		return rota;
	}

	private void validarRota(Rota rota) {
		if (rota == null) {
			throw new ValidacaoException("Informar rota.");
		}
		if (StringUtils.isBlank(rota.getNome())) {
			throw new ValidacaoException("Informar nome da rota.");
		}
		if (StringUtils.isBlank(rota.getDescricao())) {
			throw new ValidacaoException("Informar descrição da rota.");
		}
		if (rota.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se rota será ativa ou inativa.");
		}
		if (rota.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
	}

	@Override
	@Transactional
	public Rota atualizar(Rota rota) {
		validarRota(rota);

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			rota.setDtAtualizacao(new Date());
			rota.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			rota = rotaRepository.save(rota);
		} catch (Exception e) {
			log.debug("Erro ao atualizar rota: {}.", rota);
			throw new ApiException("Erro ao atualizar rota.", e);
		}
		return rota;
	}

	@Override
	public PageDTO<RotaMapping> listar(RotaFilter rotaFilter, PageDTO<RotaMapping> pageDTO) {
		try {
			List<RotaMapping> rotas = rotaRepository.listar(rotaFilter, pageDTO);

			Long total = rotaRepository.contar(rotaFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, rotas);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de rotas com os filtros: {} e paginação: {}.", rotaFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de rotas.", e);
		}
	}

	@Override
	public Rota buscar(Long cdRota) {
		try {
			return rotaRepository.findById(cdRota).orElseThrow(() -> new NaoEncontradoException("Rota não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Rota {} não encontrada.", cdRota);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar rota com o código: {}.", cdRota);
			throw new ApiException("Erro ao buscar rota com o código.", e);
		}
	}

}

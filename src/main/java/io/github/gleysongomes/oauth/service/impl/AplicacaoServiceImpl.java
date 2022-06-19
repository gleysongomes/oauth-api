package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AplicacaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.AplicacaoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Aplicacao;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.AplicacaoRepository;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class AplicacaoServiceImpl implements AplicacaoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AplicacaoRepository aplicacaoRepository;

	private final UsuarioService usuarioService;

	public AplicacaoServiceImpl(AplicacaoRepository aplicacaoRepository, UsuarioService usuarioService) {
		this.aplicacaoRepository = aplicacaoRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public Aplicacao adicionar(Aplicacao aplicacao) {
		if (aplicacao == null) {
			throw new ValidacaoException("Informar aplicação.");
		}

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		try {
			aplicacao.setDtCadastro(new Date());
			aplicacao.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			aplicacao = aplicacaoRepository.save(aplicacao);
		} catch (Exception e) {
			log.debug("Erro ao adicionar aplicação: {}.", aplicacao);
			throw new ApiException("Erro ao adicionar aplicação.", e);
		}
		return aplicacao;
	}

	@Override
	@Transactional
	public Aplicacao atualizar(Aplicacao aplicacao) {
		if (aplicacao == null) {
			throw new ValidacaoException("Informar aplicação.");
		}

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			aplicacao.setDtAtualizacao(new Date());
			aplicacao.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			aplicacao = aplicacaoRepository.save(aplicacao);
		} catch (Exception e) {
			log.debug("Erro ao atualizar aplicação: {}.", aplicacao);
			throw new ApiException("Erro ao atualizar aplicação.", e);
		}
		return aplicacao;
	}

	@Override
	public PageDTO<AplicacaoMapping> listar(AplicacaoFilter aplicacaoFilter, PageDTO<AplicacaoMapping> pageDTO) {
		try {
			List<AplicacaoMapping> aplicacoes = aplicacaoRepository.listar(aplicacaoFilter, pageDTO);

			Long total = aplicacaoRepository.contar(aplicacaoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, aplicacoes);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de aplicações com os filtros: {} e paginação: {}.", aplicacaoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de aplicações.", e);
		}
	}

	@Override
	public Aplicacao buscar(Long cdAplicacao) {
		try {
			return aplicacaoRepository.findById(cdAplicacao)
					.orElseThrow(() -> new NaoEncontradoException("Aplicação não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Aplicação {} não encontrada.", cdAplicacao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar aplicação com o código: {}.", cdAplicacao);
			throw new ApiException("Erro ao buscar aplicação.", e);
		}
	}

	@Override
	public Aplicacao buscarPorNome(String nome) {
		try {
			return aplicacaoRepository.findByNome(nome)
					.orElseThrow(() -> new NaoEncontradoException("Aplicação não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Aplicação {} não encontrada.", nome);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar aplicação pelo nome: {}.", nome);
			throw new ApiException("Erro ao buscar aplicação.", e);
		}
	}

}

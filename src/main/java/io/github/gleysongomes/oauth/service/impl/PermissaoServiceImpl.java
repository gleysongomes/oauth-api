package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Permissao;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.PermissaoRepository;
import io.github.gleysongomes.oauth.service.PermissaoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class PermissaoServiceImpl implements PermissaoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PermissaoRepository permissaoRepository;

	private final UsuarioService usuarioService;

	public PermissaoServiceImpl(PermissaoRepository permissaoRepository, UsuarioService usuarioService) {
		this.permissaoRepository = permissaoRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public Permissao adicionar(Permissao permissao) {
		validarPermissao(permissao);

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		try {
			permissao.setDtCadastro(new Date());
			permissao.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			permissao = permissaoRepository.save(permissao);
		} catch (Exception e) {
			log.debug("Erro ao adicionar permissão: {}.", permissao);
			throw new ApiException("Erro ao adicionar permissão.", e);
		}
		return permissao;
	}

	private void validarPermissao(Permissao permissao) {
		if (permissao == null) {
			throw new ValidacaoException("Informar permissão.");
		}
		if (StringUtils.isBlank(permissao.getNome())) {
			throw new ValidacaoException("Informar nome da permissão.");
		}
		if (StringUtils.isBlank(permissao.getDescricao())) {
			throw new ValidacaoException("Informar descrição da permissão.");
		}
		if (permissao.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se permissão será ativa ou inativa.");
		}
		if (permissao.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
	}

	@Override
	@Transactional
	public Permissao atualizar(Permissao permissao) {
		validarPermissao(permissao);

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			permissao.setDtAtualizacao(new Date());
			permissao.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			permissao = permissaoRepository.save(permissao);
		} catch (Exception e) {
			log.debug("Erro ao atualizar permissão: {}.", permissao);
			throw new ApiException("Erro ao atualizar permissão.", e);
		}
		return permissao;
	}

	@Override
	public PageDTO<PermissaoMapping> listar(PermissaoFilter permissaoFilter, PageDTO<PermissaoMapping> pageDTO) {
		try {
			List<PermissaoMapping> permissoes = permissaoRepository.listar(permissaoFilter, pageDTO);

			Long total = permissaoRepository.contar(permissaoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, permissoes);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de permissões com os filtros: {} e paginação: {}.", permissaoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de permissões.", e);
		}
	}

	@Override
	public Permissao buscar(Long cdPermissao) {
		try {
			return permissaoRepository.findById(cdPermissao)
					.orElseThrow(() -> new NaoEncontradoException("Permissão não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Permissão {} não encontrada.", cdPermissao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar permissão com o código: {}.", cdPermissao);
			throw new ApiException("Erro ao buscar permissão.", e);
		}
	}

}

package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AutoridadeFilter;
import io.github.gleysongomes.oauth.dto.mapping.AutoridadeMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Autoridade;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.AutoridadeRepository;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.service.AutoridadeService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class AutoridadeServiceImpl implements AutoridadeService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AutoridadeRepository autoridadeRepository;

	private final UsuarioService usuarioService;

	private final AplicacaoService aplicacaoService;

	public AutoridadeServiceImpl(AutoridadeRepository autoridadeRepository, UsuarioService usuarioService,
			AplicacaoService aplicacaoService) {
		this.autoridadeRepository = autoridadeRepository;
		this.usuarioService = usuarioService;
		this.aplicacaoService = aplicacaoService;
	}

	@Override
	@Transactional
	public Autoridade adicionar(Autoridade autoridade) {
		validarAutoridade(autoridade);

		aplicacaoService.buscar(autoridade.getCdAplicacao());

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(autoridade);

		try {
			autoridade.setDtCadastro(new Date());
			autoridade.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			autoridade = autoridadeRepository.save(autoridade);
		} catch (Exception e) {
			log.debug("Erro ao adicionar autoridade: {}.", autoridade);
			throw new ApiException("Erro ao adicionar autoridade.", e);
		}
		return autoridade;
	}

	private void validarAutoridade(Autoridade autoridade) {
		if (autoridade == null) {
			throw new ValidacaoException("Informar autoridade.");
		}
		if (StringUtils.isBlank(autoridade.getNome())) {
			throw new ValidacaoException("Informar nome do autoridade.");
		}
		if (StringUtils.isBlank(autoridade.getDescricao())) {
			throw new ValidacaoException("Informar descrição do autoridade.");
		}
		if (autoridade.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
		if (autoridade.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se autoridade será ativa ou inativa.");
		}
	}

	private void validarDuplicidade(Autoridade autoridade) {
		try {
			Optional<Autoridade> autoridadeOptional = autoridadeRepository.findByNomeAndCdAplicacao(autoridade.getNome(),
					autoridade.getCdAplicacao());

			if (autoridadeOptional.isPresent()) {
				throw new ValidacaoException("Esse nome de autoridade para essa aplicação já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Esse nome de autoridade para essa aplicação já existe: {}.", autoridade);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar autoridade pelo nome e código da aplicação: {}.", autoridade);
			throw new ApiException("Erro ao buscar autoridade pelo nome e código da aplicação.", e);
		}
	}

	@Override
	@Transactional
	public Autoridade atualizar(Autoridade autoridade) {
		validarAutoridade(autoridade);

		aplicacaoService.buscar(autoridade.getCdAplicacao());

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			autoridade.setDtAtualizacao(new Date());
			autoridade.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			autoridade = autoridadeRepository.save(autoridade);
		} catch (Exception e) {
			log.debug("Erro ao atualizar autoridade com o código: {}.", autoridade);
			throw new ApiException("Erro ao atualizar autoridade.", e);
		}
		return autoridade;
	}

	@Override
	public PageDTO<AutoridadeMapping> listar(AutoridadeFilter autoridadeFilter, PageDTO<AutoridadeMapping> pageDTO) {
		try {
			List<AutoridadeMapping> permissoes = autoridadeRepository.listar(autoridadeFilter, pageDTO);

			Long total = autoridadeRepository.contar(autoridadeFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, permissoes);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de autoridades com os filtros: {} e paginação: {}.", autoridadeFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de autoridades.", e);
		}
	}

	@Override
	public Autoridade buscar(Long cdAutoridade) {
		try {
			return autoridadeRepository.findById(cdAutoridade)
					.orElseThrow(() -> new NaoEncontradoException("Autoridade não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Autoridade {} não encontrada.", cdAutoridade);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar autoridade com o código: {}.", cdAutoridade);
			throw new ApiException("Erro ao buscar autoridade com o código.", e);
		}
	}

}

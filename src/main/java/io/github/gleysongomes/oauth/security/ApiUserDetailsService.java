package io.github.gleysongomes.oauth.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.dto.mapping.PermissaoResumoMapping;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.GrupoUsuario;
import io.github.gleysongomes.oauth.model.PermissaoUsuario;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.PermissaoRepository;
import io.github.gleysongomes.oauth.repository.UsuarioRepository;
import io.github.gleysongomes.oauth.service.GrupoUsuarioService;
import io.github.gleysongomes.oauth.service.PermissaoUsuarioService;

@Service
public class ApiUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	private final PermissaoRepository permissaoRepository;

	private final PermissaoUsuarioService permissaoUsuarioService;

	private final GrupoUsuarioService grupoUsuarioService;

	public ApiUserDetailsService(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository,
			PermissaoUsuarioService permissaoUsuarioService, GrupoUsuarioService grupoUsuarioService) {
		this.usuarioRepository = usuarioRepository;
		this.permissaoRepository = permissaoRepository;
		this.permissaoUsuarioService = permissaoUsuarioService;
		this.grupoUsuarioService = grupoUsuarioService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

		if (usuario.getFlAtivo() != null && !usuario.getFlAtivo()) {
			throw new ValidacaoException("Usuário inativo.");
		}

		return new ApiUser(usuario, getAuthorities(usuario));
	}

	private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
		Set<GrantedAuthority> authorities = new HashSet<>();

		List<PermissaoResumoMapping> permissoes = permissaoRepository.findPermissoesGrupoByCdUsuario(usuario.getCdUsuario());

		List<PermissaoUsuario> permissoesUsuario = permissaoUsuarioService
				.findByCdUsuarioAndFlAtivaIsTrue(usuario.getCdUsuario());

		List<GrupoUsuario> gruposUsuario = grupoUsuarioService.findByCdUsuarioAndFlAtivoIsTrue(usuario.getCdUsuario());

		if (permissoes != null && !permissoes.isEmpty()) {
			permissoes.forEach(permissao -> {
				if (StringUtils.isNotBlank(permissao.getNome())) {
					authorities.add(new SimpleGrantedAuthority(permissao.getNome().toUpperCase()));
				}
			});
		}

		if (permissoesUsuario != null && !permissoesUsuario.isEmpty()) {
			permissoesUsuario.forEach(permissaoUsuario -> {
				if (permissaoUsuario.getPermissao() != null
						&& StringUtils.isNotBlank(permissaoUsuario.getPermissao().getNome())) {
					authorities.add(new SimpleGrantedAuthority(permissaoUsuario.getPermissao().getNome().toUpperCase()));
				}
			});
		}

		if (gruposUsuario != null && !gruposUsuario.isEmpty()) {
			gruposUsuario.forEach(grupoUsuario -> {
				if (grupoUsuario.getGrupo() != null && StringUtils.isNotBlank(grupoUsuario.getGrupo().getNome())) {
					authorities.add(new SimpleGrantedAuthority("GRUPO_" + grupoUsuario.getGrupo().getNome().toUpperCase()));
				}
			});
		}

		return authorities;
	}

}

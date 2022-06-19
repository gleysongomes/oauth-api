package io.github.gleysongomes.oauth.webservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.OauthApiApplication;
import io.github.gleysongomes.oauth.dto.input.AdicaoUsuarioInput;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.UsuarioRepository;
import io.github.gleysongomes.oauth.util.ObjectMapperUtil;

@AutoConfigureMockMvc
@SpringBootTest(classes = OauthApiApplication.class)
public class UsuarioWebServiceTests {

	private final MockMvc usuarioMockMvc;

	private final UsuarioRepository usuarioRepository;

	public UsuarioWebServiceTests(MockMvc usuarioMockMvc, UsuarioRepository usuarioRepository) {
		this.usuarioMockMvc = usuarioMockMvc;
		this.usuarioRepository = usuarioRepository;
	}

	@Test
	@Transactional
	public void adicionar() throws Exception {
		// @formatter:off
		AdicaoUsuarioInput adicaoUsuarioInput = AdicaoUsuarioInput.builder()
				.login("teste")
				.email("teste@localhost")
				.nome("Teste")
				.senha("teste")
				.flAtivo(Boolean.TRUE)
				.build();

		usuarioMockMvc.perform(
				post("/usuarios").contentType(MediaType.APPLICATION_JSON)
						.content(ObjectMapperUtil.writeValueAsString(adicaoUsuarioInput)))
				.andExpect(status().isCreated());
		// @formatter:on

		Usuario usuario = usuarioRepository.findByLogin("teste")
				.orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado."));

		assertThat(usuario.getLogin()).isEqualTo("teste");
		assertThat(usuario.getEmail()).isEqualTo("teste@localhost");
		assertThat(usuario.getNome()).isEqualTo("Teste");
		assertThat(usuario.getHashSenha()).isEqualTo("teste");
		assertThat(usuario.getFlAtivo()).isEqualTo(Boolean.TRUE);
	}

}

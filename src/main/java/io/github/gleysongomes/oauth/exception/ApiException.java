package io.github.gleysongomes.oauth.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -3968152296017973078L;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Getter
	private String mensagem;

	@Getter
	private UUID cdException;

	@Getter
	private String causaRaiz;

	public ApiException(String mensagem) {
		super(mensagem);
		this.mensagem = mensagem;
	}

	public ApiException(String mensagem, Throwable throwable) {
		this(mensagem, throwable, true);
	}

	public ApiException(String mensagem, Throwable throwable, boolean flTrace) {
		super(mensagem, throwable);
		this.mensagem = mensagem;

		this.cdException = UUID.randomUUID();
		this.causaRaiz = ExceptionUtils.getRootCauseMessage(throwable);

		log.error("IE-{}: INÍCIO DA EXCEÇÃO", this.cdException);
		log.error("CR-{}: CAUSA RAIZ: {}", this.cdException, this.causaRaiz);

		if (flTrace) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			this.printStackTrace(pw);

			log.error("{}", sw);
		}

		log.error("FE-{}: FINAL DA EXCEÇÃO", this.cdException);
	}

}

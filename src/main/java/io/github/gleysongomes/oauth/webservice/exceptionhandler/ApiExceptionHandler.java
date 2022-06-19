package io.github.gleysongomes.oauth.webservice.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String DADOS_INFORMADOS_INCORRETAMENTE = "Dados informados incorretamente.";

	private static final String ERRO_INTERNO_SISTEMA = "Ocorreu um erro interno no sistema.";

	private final MessageSource messageSource;

	public ApiExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleBinding(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleBinding(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleBinding(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request,
			BindingResult bindingResult) {
		List<Problem.Object> objects = bindingResult.getAllErrors().stream().map(objectError -> {

			String name = objectError instanceof FieldError ? ((FieldError) objectError).getField()
					: objectError.getObjectName();
			String userMessage = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			Problem.Object object = Problem.Object.builder().name(name).userMessage(userMessage).build();

			return object;
		}).collect(Collectors.toList());

		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		Problem problem = createProblemBuilder(status, problemType).objects(objects)
				.userMessage(DADOS_INFORMADOS_INCORRETAMENTE).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;

		Problem problem = createProblemBuilder(status, problemType).userMessage(DADOS_INFORMADOS_INCORRETAMENTE)
				.rootCause(ExceptionUtils.getRootCauseMessage(ex)).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacao(ValidacaoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;

		Problem problem = createProblemBuilder(status, problemType).userMessage(ex.getMensagem())
				.exceptionId(ex.getCdException()).rootCause(ex.getCausaRaiz()).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<Object> handleNaoEncontrado(NaoEncontradoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.NAO_ENCONTRADO;

		Problem problem = createProblemBuilder(status, problemType).userMessage(ex.getMensagem())
				.exceptionId(ex.getCdException()).rootCause(ex.getCausaRaiz()).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Object> handleApi(ApiException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;

		Problem problem = createProblemBuilder(status, problemType).userMessage(ex.getMensagem())
				.exceptionId(ex.getCdException()).rootCause(ex.getCausaRaiz()).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;

		Problem problem = createProblemBuilder(status, problemType).userMessage(ERRO_INTERNO_SISTEMA)
				.rootCause(ExceptionUtils.getRootCauseMessage(ex)).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_INTEGRIDADE;

		Problem problem = createProblemBuilder(status, problemType).userMessage(problemType.getTitle())
				.rootCause(ExceptionUtils.getRootCauseMessage(ex)).build();

		return ResponseEntity.status(status).body(problem);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;

		Problem problem = createProblemBuilder(status, problemType).userMessage(problemType.getTitle())
				.rootCause(ExceptionUtils.getRootCauseMessage(ex)).build();

		return ResponseEntity.status(status).body(problem);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
			body = createProblemBuilder(status, problemType).userMessage(ERRO_INTERNO_SISTEMA)
					.rootCause(ExceptionUtils.getRootCauseMessage(ex)).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType) {
		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).title(problemType.getTitle());
	}

}

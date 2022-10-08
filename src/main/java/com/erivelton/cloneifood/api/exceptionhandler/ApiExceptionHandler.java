package com.erivelton.cloneifood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.erivelton.cloneifood.domain.exception.EntidadeEmUsoException;
import com.erivelton.cloneifood.domain.exception.EntidadeNaoEncontradaException;
import com.erivelton.cloneifood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	private static final String MSG_GENERICA = "Ocorreu um erro interno inesperado no sistema." + 
			" Tente novamente e se o problema persistir, entre em contato" + 
			" com o administrador do sistema.";  

	//Outra exceção a ser mapeada é o MismatchedInputException que é quando insere um valor diferente que um campo espera como exemplo
	//"taxaFrete":true	
	
	//Outro possível erro similar ao InvalidFormat é o JsonMappingException que ocorre quando não é colocado aspas no valor do campo: "id": a
	
	//Um outro ponto sobre a exceção genérica HttpMessageNotReadableException(erro ocorrido durante a desserialização de um JSON)
//	se refere a erros no formato da requisição, inclusive podendo ser XML, portanto as exceções gerada a partir dele não funcionariam se 
//	fossem mapeadas com @ExceptionHandler, o HttpMessageNot.. é lançada pelo Spring, já o InvalidFormat é lançada pelo Jackson

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if(rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredProperty((IgnoredPropertyException) rootCause, headers, status, request);
		}

		if(rootCause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException) rootCause, headers, status, request);
		}
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL,
				"O corpo da requisição está inválido. Verifique erro de sintaxe").build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, String.format("Recurso %s não encontrado", ex.getRequestURL())).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<DetailField> detailFields = ex.getBindingResult().getFieldErrors().stream()
				.map(field -> DetailField.builder()
						.name(field.getField())
						.userMessage(messageSource.getMessage(field, LocaleContextHolder.getLocale()))
						.build())
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, "Um ou mais campos da requisição estão inválidos")
			.detailFields(detailFields)
			.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEstadoNaoEncontrado(EntidadeNaoEncontradaException e, WebRequest request) {
		
		Problem problem = createProblemBuilder(HttpStatus.NOT_FOUND, ProblemType.ENTIDADE_NAO_ENCONTRADA,
				e.getMessage()).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneric(Exception ex, WebRequest request){
		
		Problem problem = createProblemBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ProblemType.ERRO_DO_SISTEMA, 
				"Ocorreu um erro interno inesperado no sistema." + 
				" Tente novamente e se o problema persistir, entre em contato" + 
				" com o administrador do sistema.").build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		
		Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.ERRO_DE_NEGOCIO, e.getMessage())
				.build();
	
		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException e, WebRequest request) {
		
		Problem problem = createProblemBuilder(HttpStatus.CONFLICT, ProblemType.ENTIDADE_EM_USO, e.getMessage())
				.build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
//		ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.toList()); esse cara traz os campos que geraram a exceção
//		InvalidFormat, porém se tiver dois campos que geraram erros como taxaFrete="12d" e id="1" essa lista só mostrará o campo taxaFrete
//		como erro na resposta da api, depois de corrigido o campo taxaFrete aí sim mostrará o erro no campo id
		
		Map<String, Object> dados = dadosProblem(ex);
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s',"
				+ " que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", 
				dados.get("field"), ex.getValue(), ex.getTargetType().getSimpleName()
		);
		
		Problem problem = createProblemBuilder(status, (ProblemType) dados.get("problemType"), detail)
				.userMessage(MSG_GENERICA)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleIgnoredProperty(IgnoredPropertyException rootCause, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		Map<String, Object> dados = dadosProblem(rootCause);
		Problem problem = createProblemBuilder(status, (ProblemType) dados.get("problemType"), "Propriedade " + dados.get("field") + " não deve ser inserido!")
				.build();
		
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException rootCause,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> dados = dadosProblem(rootCause);
		Problem problem = createProblemBuilder(status, (ProblemType) dados.get("problemType"), "Propriedade " + dados.get("field") + " não reconhecida!").build();
		
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		Problem problem = createProblemBuilder(
				HttpStatus.BAD_REQUEST,
				ProblemType.PARAMETRO_INVALIDO, 
				"Erro ao passar o valor '" + ex.getValue() + "' para o parâmetro de URL '" + ex.getName()
				+ "'. Insira um valor do tipo " + ex.getRequiredType().getSimpleName()
				).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder().status(status.value()).title(status.getReasonPhrase()).timestamp(LocalDateTime.now()).build();
		} else if (body instanceof String) {
			body = Problem.builder().status(status.value()).title((String) body).build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Map<String, Object> dadosProblem(MismatchedInputException rootCause) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String field = rootCause.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining(".")
		);
		
		return Map.of("field", field, "problemType", problemType);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.timestamp(LocalDateTime.now())
				.detail(detail);
	}
}

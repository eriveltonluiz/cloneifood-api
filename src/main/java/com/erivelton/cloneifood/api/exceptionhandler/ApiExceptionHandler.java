package com.erivelton.cloneifood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.erivelton.cloneifood.domain.exception.EntidadeEmUsoException;
import com.erivelton.cloneifood.domain.exception.EntidadeNaoEncontradaException;
import com.erivelton.cloneifood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

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
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL,
				"O corpo da requisição está inválido. Verifique erro de sintaxe").build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}


	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEstadoNaoEncontradoException(EntidadeNaoEncontradaException e, WebRequest request) {
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage())
//				.build();
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);

		// solução do curso especialista rest
//		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
//		
//		Problem problem = createProblemBuilder(HttpStatus.NOT_FOUND, problemType, e.getMessage()).build();

//		Problem problem = ProblemType.ENTIDADE_NAO_ENCONTRADA.setarDadosDaExcecao(e.getMessage()).build();
		Problem problem = createProblemBuilder(HttpStatus.NOT_FOUND, ProblemType.ENTIDADE_NAO_ENCONTRADA,
				e.getMessage()).build();
		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, ProblemType.ERRO_DE_NEGOCIO, e.getMessage())
				.build();
		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		Problem problem = createProblemBuilder(HttpStatus.CONFLICT, ProblemType.ENTIDADE_EM_USO, e.getMessage())
				.build();
		return handleExceptionInternal(e, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder().status(status.value()).title(status.getReasonPhrase()).build();
		} else if (body instanceof String) {
			body = Problem.builder().status(status.value()).title((String) body).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
//		ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.toList()); esse cara traz os campos que geraram a exceção
//		InvalidFormat, porém se tiver dois campos que geraram erros como taxaFrete="12d" e id="1" essa lista só mostrará o campo taxaFrete
//		como erro na resposta da api, depois de corrigido o campo taxaFrete aí sim mostrará o erro no campo id
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining(".")
		);
		
		ex.getPath().forEach(ref -> System.out.println("----- " + ref.getFieldName()));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", 
				path, ex.getValue(), ex.getTargetType().getSimpleName()
		);
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);
	}
}

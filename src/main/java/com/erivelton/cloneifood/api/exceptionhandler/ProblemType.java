package com.erivelton.cloneifood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem incompreensivel", "Mensagem incompreensível"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_DE_NEGOCIO("/erro-de-negocio", "Erro de negócio"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ERRO_DO_SISTEMA("/erro-do-sistema", "Erro do sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://localhost:8080/" + path;
		this.title = title;
	}
}

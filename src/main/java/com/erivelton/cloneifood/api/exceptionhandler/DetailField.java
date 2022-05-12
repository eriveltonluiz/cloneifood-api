package com.erivelton.cloneifood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailField {

	private String name;
	private String userMessage;
}

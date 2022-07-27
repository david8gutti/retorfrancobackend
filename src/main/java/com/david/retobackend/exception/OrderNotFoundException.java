package com.david.retobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class OrderNotFoundException extends HttpStatusCodeException  {

	private static final long serialVersionUID = 8327469516122012400L;

	public OrderNotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}

	public OrderNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}

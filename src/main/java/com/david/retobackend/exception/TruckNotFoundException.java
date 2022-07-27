package com.david.retobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class TruckNotFoundException extends HttpStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4869507455922185943L;

	
	public TruckNotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}

	public TruckNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}

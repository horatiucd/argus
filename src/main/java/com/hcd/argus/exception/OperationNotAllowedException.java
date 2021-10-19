package com.hcd.argus.exception;

import java.io.Serial;

public class OperationNotAllowedException extends RuntimeException {
	
	@Serial
    private static final long serialVersionUID = 1L;

	public OperationNotAllowedException(String message) {
        super(message);
    }
}

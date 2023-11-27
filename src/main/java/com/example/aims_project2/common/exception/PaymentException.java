package com.example.aims_project2.common.exception;
import java.lang.RuntimeException;

public class PaymentException extends RuntimeException {
	public PaymentException(String message) {
		super(message);
	}
}

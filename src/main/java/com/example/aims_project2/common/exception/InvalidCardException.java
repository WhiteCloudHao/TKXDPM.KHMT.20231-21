package com.example.aims_project2.common.exception;

public class InvalidCardException extends PaymentException {
	public InvalidCardException() {
		super("ERROR: Invalid card!");
	}
}

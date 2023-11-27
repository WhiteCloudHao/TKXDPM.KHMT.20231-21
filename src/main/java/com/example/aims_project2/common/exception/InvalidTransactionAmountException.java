package com.example.aims_project2.common.exception;

public class InvalidTransactionAmountException extends PaymentException {
	public InvalidTransactionAmountException() {
		super("ERROR: Invalid Transaction Amount!");
	}
}

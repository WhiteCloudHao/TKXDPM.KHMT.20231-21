package com.example.aims_project2.common.exception;

public class SuspiciousTransactionException extends PaymentException {
	public SuspiciousTransactionException() {
		super("ERROR: Suspicious Transaction Report!");
	}
}

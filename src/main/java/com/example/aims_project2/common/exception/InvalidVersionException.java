package com.example.aims_project2.common.exception;

public class InvalidVersionException extends PaymentException{
	public InvalidVersionException() {
		super("ERROR: Invalid Version Information!");
	}
}

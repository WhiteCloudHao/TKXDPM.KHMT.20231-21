package com.example.aims_project2.common.exception;




public class InternalServerErrorException extends PaymentException {

	public InternalServerErrorException() {
		super("ERROR: Internal Server Error!");
	}

}

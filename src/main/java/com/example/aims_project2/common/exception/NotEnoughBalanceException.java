package com.example.aims_project2.common.exception;

public class NotEnoughBalanceException extends PaymentException{

	public NotEnoughBalanceException() {
		super("ERROR: Not enough balance in card!");
	}

}

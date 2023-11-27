package com.example.aims_project2.common.exception;

public class NotEnoughTransactionInfoException extends PaymentException {
public NotEnoughTransactionInfoException() {
	super("ERROR: Not Enough Transcation Information");
}
}

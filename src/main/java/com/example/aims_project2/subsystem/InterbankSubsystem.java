package com.example.aims_project2.subsystem;

import com.example.aims_project2.common.exception.InternalServerErrorException;
import com.example.aims_project2.common.exception.InvalidCardException;
import com.example.aims_project2.common.exception.NotEnoughBalanceException;
import com.example.aims_project2.entity.payment.CreditCard;
import com.example.aims_project2.entity.payment.PaymentTransaction;
import com.example.aims_project2.subsystem.interbank.InterbankSubsystemController;

/***
 * The {@code InterbankSubsystem} class is used to communicate with the
 * Interbank to make transaction.
 * 
 * @author hieud
 *
 */
public class InterbankSubsystem implements InterbankInterface {

	/**
	 * Represent the controller of the subsystem
	 */
	private InterbankSubsystemController ctrl;

	/**
	 * Initializes a newly created {@code InterbankSubsystem} object so that it
	 * represents an Interbank subsystem.
	 */
	public InterbankSubsystem() {
		String str = new String();
		this.ctrl = new InterbankSubsystemController();
	}

	/**
	 * @see InterbankInterface#payOrder(com.example.aims_project2.entity.payment.CreditCard, int,
	 *      java.lang.String)
	 */
	public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.payOrder(card, amount, contents);
		return transaction;
	}

	/**
	 * @see InterbankInterface#refund(com.example.aims_project2.entity.payment.CreditCard, int,
	 *      java.lang.String)
	 */
	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.refund(card, amount, contents);
		return transaction;
	}
}

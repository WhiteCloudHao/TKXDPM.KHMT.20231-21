package com.example.aims_project2.subsystem;

import com.example.aims_project2.common.exception.PaymentException;
import com.example.aims_project2.common.exception.UnrecognizedException;
import com.example.aims_project2.entity.payment.CreditCard;
import com.example.aims_project2.entity.payment.PaymentTransaction;

/**
 * The {@code InterbankInterface} class is used to communicate with the
 * {@link com.example.aims_project2.subsystem.InterbankSubsystem InterbankSubsystem} to make transaction
 * 
 * @author hieud
 * 
 */
public interface InterbankInterface {

	/**
	 * Pay order, and then return the payment transaction
	 * 
	 * @param card     - the credit card used for payment
	 * @param amount   - the amount to pay
	 * @param contents - the transaction contents
	 * @return {@link com.example.aims_project2.entity.payment.PaymentTransaction PaymentTransaction} - if the
	 *         payment is successful
	 * @throws PaymentException      if responded with a pre-defined error code
	 * @throws UnrecognizedException if responded with an unknown error code or
	 *                               something goes wrong
	 */
	public abstract PaymentTransaction payOrder(CreditCard card, int amount, String contents)
			throws PaymentException, UnrecognizedException;

	/**
	 * Refund, and then return the payment transaction
	 * 
	 * @param card     - the credit card which would be refunded to
	 * @param amount   - the amount to refund
	 * @param contents - the transaction contents
	 * @return {@link com.example.aims_project2.entity.payment.PaymentTransaction PaymentTransaction} - if the
	 *         payment is successful
	 * @throws PaymentException      if responded with a pre-defined error code
	 * @throws UnrecognizedException if responded with an unknown error code or
	 *                               something goes wrong
	 */
	public abstract PaymentTransaction refund(CreditCard card, int amount, String contents)
			throws PaymentException, UnrecognizedException;

}

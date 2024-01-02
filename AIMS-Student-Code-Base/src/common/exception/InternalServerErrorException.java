package common.exception;;

/**
Coupling: Moderate level of coupling due to the inheritance from the PaymentException class. This coupling is reasonable in the context of having a hierarchy of payment-related exceptions.

Cohesion: Reasonable level of cohesion, focusing on representing internal server errors related to payments.
 */
public class InternalServerErrorException extends PaymentException {

	public InternalServerErrorException() {
		super("ERROR: Internal Server Error!");
	}

}

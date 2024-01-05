package common.exception;

/**

Coupling: The class has moderate coupling with the RuntimeException Java exception hierarchy.

Cohesion: The class exhibits high cohesion as it is focused on representing exceptions related to payments.
 */
public class PaymentException extends RuntimeException {
	public PaymentException(String message) {
		super(message);
	}
}

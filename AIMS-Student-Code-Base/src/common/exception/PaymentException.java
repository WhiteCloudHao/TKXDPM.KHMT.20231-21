package common.exception;;

// low coupling because uncoupling
public class PaymentException extends RuntimeException {
	public PaymentException(String message) {
		super(message);
	}
}

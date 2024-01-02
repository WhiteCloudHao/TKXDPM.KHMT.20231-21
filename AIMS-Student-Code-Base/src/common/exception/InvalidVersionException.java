package common.exception;

/**
Coupling: The class has a moderate level of coupling due to its inheritance relationship with the PaymentException class.

Cohesion: The class exhibits high cohesion as it is focused on representing exceptions related to invalid version.
 */
public class InvalidVersionException extends PaymentException{
	public InvalidVersionException() {
		super("ERROR: Invalid Version Information!");
	}
}

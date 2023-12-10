package common.exception;;

/**
Coupling: The class has a moderate level of coupling with the Runtime Exception Java exception hierarchy.

Cohesion: The class exhibits reasonable cohesion as it is focused on representing exceptions related to unrecognized or unexpected situations.
 */
public class UnrecognizedException extends RuntimeException {
	public UnrecognizedException() {
		super("ERROR: Something went wrowng!");
	}
}

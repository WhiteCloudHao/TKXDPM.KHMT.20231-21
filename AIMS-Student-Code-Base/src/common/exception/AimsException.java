package common.exception;;

/**
 * The AimsException wraps all unchecked exceptions You can use this
 * exception to inform
 * 
 * @author nguyenlm
 */

 /**
 Coupling : Low coupling because it seems self-contained and doesn't rely on other classes. 

 Cohesion: High cohesion, focusing on the representation of exceptions. 
 */
public class AimsException extends RuntimeException {

    public AimsException() {

	}

	public AimsException(String message) {
		super(message);
	}
}
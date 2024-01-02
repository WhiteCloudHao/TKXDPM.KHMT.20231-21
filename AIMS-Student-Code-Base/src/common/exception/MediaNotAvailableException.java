package common.exception;;

/**
 * The MediaNotAvailableException wraps all unchecked exceptions You can use this
 * exception to inform
 * 
 * @author nguyenlm
 */

 /**
 Coupling: The class has a moderate level of coupling due to its inheritance relationship with the AimsException class.

Cohesion: The class exhibits high cohesion as it is focused on representing exceptions related to media not being available.
  */
public class MediaNotAvailableException extends AimsException {

	private static final long serialVersionUID = 1091337136123906298L;

	public MediaNotAvailableException() {

	}

	public MediaNotAvailableException(String message) {
		super(message);
	}

}
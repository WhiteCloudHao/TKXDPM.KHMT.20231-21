package common.exception;;

/**
 * The MediaUpdateException wraps all unchecked exceptions You can use this
 * exception to inform
 * 
 * @author nguyenlm
 */

  /**
 Coupling: The class has a moderate level of coupling due to its inheritance relationship with the AimsException class.

Cohesion: The class exhibits high cohesion as it is focused on representing exceptions related to media update.
  */
public class MediaUpdateException extends AimsException {

	private static final long serialVersionUID = 1091337136123906298L;

	public MediaUpdateException() {

	}

	public MediaUpdateException(String message) {
		super(message);
	}

}

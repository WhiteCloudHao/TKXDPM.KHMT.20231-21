package common.exception;;

/**
 * The ProcessInvoiceException wraps all unchecked exceptions You can use this
 * exception to inform 
 * 
 * @author nguyenlm
 */

 /**

Coupling: The class has moderate coupling with the RuntimeException Java exception hierarchy.

Cohesion: The class exhibits high cohesion as it is focused on representing exceptions related to process invoice.
 */
public class ProcessInvoiceException extends AimsException {

	private static final long serialVersionUID = 1091337136123906298L;

	public ProcessInvoiceException() {

	}

	public ProcessInvoiceException(String message) {
		super(message);
	}

}

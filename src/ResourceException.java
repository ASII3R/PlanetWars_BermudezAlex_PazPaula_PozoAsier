
public class ResourceException extends Exception {
	
	public ResourceException() {
		super("Ha ocurrido un error");
	}
	
	// Error Custom
	public ResourceException(String s) {
		super(s);
	}
}

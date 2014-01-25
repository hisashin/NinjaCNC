package st.tori.cnc.stencil.gerber.exception;

public class IllegalReflectionException extends GerberException {

	private Exception exception;
	
	public IllegalReflectionException(Exception exception) {
		super("Cannot call reflection on creating GStatement");
	}
	
	public Exception getException() {
		return exception;
	}
}

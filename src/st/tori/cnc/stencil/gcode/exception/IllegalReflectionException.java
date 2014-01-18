package st.tori.cnc.stencil.gcode.exception;

public class IllegalReflectionException extends GCodeException {

	private Exception exception;
	
	public IllegalReflectionException(Exception exception) {
		super("Cannot call reflection on creating GAction");
	}
	
	public Exception getException() {
		return exception;
	}
}

package st.tori.cnc.stencil.gcode.exception;

public class GCodeException extends Exception {

	private String message;
	
	public GCodeException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}

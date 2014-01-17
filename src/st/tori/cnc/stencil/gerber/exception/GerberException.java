package st.tori.cnc.stencil.gerber.exception;

public class GerberException extends Exception {

	private String message;
	
	public GerberException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}

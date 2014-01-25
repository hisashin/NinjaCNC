package st.tori.cnc.stencil.canvas.exception;

public class DrawException extends Exception {

	private String message;
	
	public DrawException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}

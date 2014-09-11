package st.tori.cnc.stencil.dxf.exception;

public class DxfException extends Exception {

	private String message;
	
	public DxfException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}

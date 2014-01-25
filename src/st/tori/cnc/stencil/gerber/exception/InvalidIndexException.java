package st.tori.cnc.stencil.gerber.exception;

public class InvalidIndexException extends GerberException {

	public InvalidIndexException(String prefix, int gIndex) {
		super(prefix+gIndex+" is invalid");
	}
	
}

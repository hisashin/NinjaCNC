package st.tori.cnc.stencil.gcode.exception;

public class InvalidIndexException extends GCodeException {

	public InvalidIndexException(String prefix, int gIndex) {
		super(prefix+gIndex+" is invalid");
	}
	
}

package st.tori.cnc.stencil.gcode.exception;

public class UnsupportedPrefixException extends GCodeException {

	public UnsupportedPrefixException(String prefix) {
		super("Sorry "+prefix+" is not supported yet.Would you do that?");
	}
	
}

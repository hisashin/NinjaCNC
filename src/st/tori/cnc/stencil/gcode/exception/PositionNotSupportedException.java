package st.tori.cnc.stencil.gcode.exception;

public class PositionNotSupportedException extends GCodeException {

	public PositionNotSupportedException(String line) {
		super("Cannot set XYZ properly:"+line);
	}
	
}

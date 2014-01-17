package st.tori.cnc.stencil.gcode.exception;

public class SpeedNotSupportedException extends GCodeException {

	public SpeedNotSupportedException(String line) {
		super("Cannot set F properly:"+line);
	}
	
}

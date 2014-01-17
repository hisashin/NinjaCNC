package st.tori.cnc.stencil.gcode.exception;

public class NoSpecifiedUnitException extends GCodeException {

	public NoSpecifiedUnitException() {
		super("G20/G21 is not yet specified");
	}
	
}

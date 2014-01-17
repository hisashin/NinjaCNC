package st.tori.cnc.stencil.gcode.exception;

public class NoSpecifiedProgramException extends GCodeException {

	public NoSpecifiedProgramException() {
		super("G90/G91 is not yet specified");
	}
	
}

package st.tori.cnc.stencil.gcode.exception;

public class NoLastActionExistsException extends GCodeException {

	public NoLastActionExistsException() {
		super("Data error.Trying to inherit nil G!");
	}
	
}

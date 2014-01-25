package st.tori.cnc.stencil.gerber.exception;

public class NoLastStatementExistsException extends GerberException {

	public NoLastStatementExistsException() {
		super("Data error.Trying to inherit nil G!");
	}
	
}

package st.tori.cnc.stencil.gerber.exception;

public class UnsupportedPrefixException extends GerberException {

	public UnsupportedPrefixException(String prefix) {
		super("Sorry "+prefix+" is not supported yet.Check file is correct.");
	}
	
}

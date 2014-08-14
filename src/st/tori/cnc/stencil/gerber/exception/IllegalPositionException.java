package st.tori.cnc.stencil.gerber.exception;


public class IllegalPositionException extends GerberException {

	public IllegalPositionException(String positionStr) {
		super("Cannot parse '"+positionStr+"' as position");
	}
	
}

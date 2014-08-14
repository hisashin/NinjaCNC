package st.tori.cnc.stencil.gerber.exception;



public class ApertureNotDefinedException extends GerberException {

	public ApertureNotDefinedException(int dcode) {
		super("Aperture D"+dcode+" is not defined yet");
	}
	
}

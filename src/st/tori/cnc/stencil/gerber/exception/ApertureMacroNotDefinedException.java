package st.tori.cnc.stencil.gerber.exception;



public class ApertureMacroNotDefinedException extends GerberException {

	public ApertureMacroNotDefinedException(int dcode, String name) {
		super("Aperture macro "+name+" is not defined yet");
	}
	
}

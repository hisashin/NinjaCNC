package st.tori.cnc.stencil.gerber.exception;



public class UnsupportedApertureException extends GerberException {

	public UnsupportedApertureException(int dcode, String type, String modifiersStr) {
		super("%ADD"+dcode+type+","+modifiersStr+"% is not yet supported.would you?");
	}
	
}

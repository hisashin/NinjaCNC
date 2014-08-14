package st.tori.cnc.stencil.gerber.exception;

public class UnsupportedMacroException extends GerberException {

	public UnsupportedMacroException(int primitiveCode, String modifiersStr) {
		super("Macro '"+primitiveCode+","+modifiersStr+"*' is not yet supported.would you?");
	}
	
}

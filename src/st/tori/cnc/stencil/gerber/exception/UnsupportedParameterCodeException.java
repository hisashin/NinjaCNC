package st.tori.cnc.stencil.gerber.exception;


public class UnsupportedParameterCodeException extends GerberException {

	public UnsupportedParameterCodeException(String parameterCode, String modifiers) {
		super(parameterCode+modifiers+" is not yet supported.would you?");
	}
	
}

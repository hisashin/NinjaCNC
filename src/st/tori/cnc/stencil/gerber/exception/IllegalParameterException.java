package st.tori.cnc.stencil.gerber.exception;


public class IllegalParameterException extends GerberException {

	public IllegalParameterException(int lineIndex, String parameterCode) {
		super("Parameter %"+parameterCode+" on line"+lineIndex+" is not properly closed by %.");
	}
	
}

package st.tori.cnc.stencil.gerber.exception;



public class ArithmeticExpressionUnsupportedException extends GerberException {

	public ArithmeticExpressionUnsupportedException(String expression) {
		super("Arithmetic expression '"+expression+"' is not yet supported.Would you?");
	}
	
}

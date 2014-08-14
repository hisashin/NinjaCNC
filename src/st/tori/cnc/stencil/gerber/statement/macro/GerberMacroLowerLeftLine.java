package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public class GerberMacroLowerLeftLine extends GerberMacroCenterLine {

	public GerberMacroLowerLeftLine(int exposure, ArithmeticExpressionDouble width, ArithmeticExpressionDouble height, ArithmeticExpressionDouble x, ArithmeticExpressionDouble y, ArithmeticExpressionDouble rotationAngle) {
		super(exposure, width, height, x, y, rotationAngle);
	}
	
}

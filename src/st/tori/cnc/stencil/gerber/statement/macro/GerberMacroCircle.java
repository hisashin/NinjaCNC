package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public class GerberMacroCircle extends GerberMacro {

	protected ArithmeticExpressionDouble diameter;
	protected ArithmeticExpressionDouble x;
	protected ArithmeticExpressionDouble y;
	
	public GerberMacroCircle(int exposure, ArithmeticExpressionDouble diameter, ArithmeticExpressionDouble x, ArithmeticExpressionDouble y) {
		super(exposure, new ArithmeticExpressionDouble("0.0"));
		this.diameter = diameter;
		this.x = x;
		this.y = y;
	}
	
	public ArithmeticExpressionDouble getX(){	return x;	}
	public ArithmeticExpressionDouble getY(){	return y;	}
	
}

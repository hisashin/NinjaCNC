package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public class GerberMacroThermal extends GerberMacro {

	protected ArithmeticExpressionDouble x;
	protected ArithmeticExpressionDouble y;
	protected ArithmeticExpressionDouble outerDiameter;
	protected ArithmeticExpressionDouble innerDiameter;
	protected ArithmeticExpressionDouble gapThickness;
	
	public GerberMacroThermal(ArithmeticExpressionDouble x, ArithmeticExpressionDouble y, ArithmeticExpressionDouble outerDiameter, ArithmeticExpressionDouble innerDiameter, ArithmeticExpressionDouble gapThickness, ArithmeticExpressionDouble rotationAngle) {
		super(1, rotationAngle);
		this.x = x;
		this.y = y;
		this.outerDiameter = outerDiameter;
		this.innerDiameter = innerDiameter;
		this.gapThickness = gapThickness;
	}
	
	public ArithmeticExpressionDouble getX(){	return x;	}
	public ArithmeticExpressionDouble getY(){	return y;	}
	public ArithmeticExpressionDouble getOuterDiameter(){	return outerDiameter;	}
	public ArithmeticExpressionDouble getInnerDiameter(){	return innerDiameter;	}
	public ArithmeticExpressionDouble getGapThickness(){	return gapThickness;	}
	
}

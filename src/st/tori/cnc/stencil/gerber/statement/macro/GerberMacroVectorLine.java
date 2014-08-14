package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public class GerberMacroVectorLine extends GerberMacro {

	protected ArithmeticExpressionDouble lineWidth;
	protected ArithmeticExpressionDouble xStart;
	protected ArithmeticExpressionDouble yStart;
	protected ArithmeticExpressionDouble xEnd;
	protected ArithmeticExpressionDouble yEnd;
	
	public GerberMacroVectorLine(int exposure, ArithmeticExpressionDouble lineWidth, ArithmeticExpressionDouble xStart, ArithmeticExpressionDouble yStart, ArithmeticExpressionDouble xEnd, ArithmeticExpressionDouble yEnd, ArithmeticExpressionDouble rotationAngle) {
		super(exposure, rotationAngle);
		this.lineWidth = lineWidth;
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
	
	public ArithmeticExpressionDouble getLineWidth(){	return lineWidth;	}
	public ArithmeticExpressionDouble getXStart(){	return xStart;	}
	public ArithmeticExpressionDouble getYStart(){	return yStart;	}
	public ArithmeticExpressionDouble getXEnd(){	return xEnd;	}
	public ArithmeticExpressionDouble getYEnd(){	return yEnd;	}
	
}

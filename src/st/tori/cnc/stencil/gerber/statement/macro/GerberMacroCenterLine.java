package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public class GerberMacroCenterLine extends GerberMacro {

	protected ArithmeticExpressionDouble width;
	protected ArithmeticExpressionDouble height;
	protected ArithmeticExpressionDouble x;
	protected ArithmeticExpressionDouble y;
	
	public GerberMacroCenterLine(int exposure, ArithmeticExpressionDouble width, ArithmeticExpressionDouble height, ArithmeticExpressionDouble x, ArithmeticExpressionDouble y, ArithmeticExpressionDouble rotationAngle) {
		super(exposure, rotationAngle);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public ArithmeticExpressionDouble getWidth(){	return width;	}
	public ArithmeticExpressionDouble getHeight(){	return height;	}
	public ArithmeticExpressionDouble getX(){	return x;	}
	public ArithmeticExpressionDouble getY(){	return y;	}
	
}

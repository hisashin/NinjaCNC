package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;
import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionInt;


public class GerberMacroPolygon extends GerberMacro {

	protected ArithmeticExpressionInt numberOfVertices;
	protected ArithmeticExpressionDouble x;
	protected ArithmeticExpressionDouble y;
	protected ArithmeticExpressionDouble diameterOfCircumscribedCircle;
	
	public GerberMacroPolygon(int exposure, ArithmeticExpressionInt numberOfVertices, ArithmeticExpressionDouble x, ArithmeticExpressionDouble y, ArithmeticExpressionDouble diameterOfCircumscribedCircle, ArithmeticExpressionDouble rotationAngle) {
		super(exposure, rotationAngle);
		this.numberOfVertices = numberOfVertices;
		this.x = x;
		this.y = y;
		this.diameterOfCircumscribedCircle = diameterOfCircumscribedCircle;
	}
	
	public ArithmeticExpressionInt getNumberOfVertices(){	return numberOfVertices;	}
	public ArithmeticExpressionDouble getX(){	return x;	}
	public ArithmeticExpressionDouble getY(){	return y;	}
	public ArithmeticExpressionDouble getDiameterOfCircumscribedCircle(){	return diameterOfCircumscribedCircle;	}
	
}

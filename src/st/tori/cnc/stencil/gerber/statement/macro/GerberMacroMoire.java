package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;
import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionInt;


public class GerberMacroMoire extends GerberMacro {

	protected ArithmeticExpressionDouble x;
	protected ArithmeticExpressionDouble y;
	protected ArithmeticExpressionDouble outerDiameter;
	protected ArithmeticExpressionDouble thickness;
	protected ArithmeticExpressionDouble gap;
	protected ArithmeticExpressionInt maximumNumberOfRings;
	protected ArithmeticExpressionDouble crossHairThickness;
	protected ArithmeticExpressionDouble crossHairLength;
	
	public GerberMacroMoire(ArithmeticExpressionDouble x, ArithmeticExpressionDouble y, ArithmeticExpressionDouble outerDiameter, ArithmeticExpressionDouble thickness, ArithmeticExpressionDouble gap, ArithmeticExpressionInt maximumNumberOfRings, ArithmeticExpressionDouble crossHairThickness, ArithmeticExpressionDouble crossHairLength, ArithmeticExpressionDouble rotationAngle) {
		super(1, rotationAngle);
		this.x = x;
		this.y = y;
		this.outerDiameter = outerDiameter;
		this.thickness = thickness;
		this.gap = gap;
		this.maximumNumberOfRings = maximumNumberOfRings;
		this.crossHairThickness = crossHairThickness;
		this.crossHairLength = crossHairLength;
	}
	
	public ArithmeticExpressionDouble getX(){	return x;	}
	public ArithmeticExpressionDouble getY(){	return y;	}
	public ArithmeticExpressionDouble getOuterDiameter(){	return outerDiameter;	}
	public ArithmeticExpressionDouble getThickness(){	return thickness;	}
	public ArithmeticExpressionDouble getGap(){	return gap;	}
	public ArithmeticExpressionInt getMaximumNumberOfRings(){	return maximumNumberOfRings;	}
	public ArithmeticExpressionDouble getCrossHairThickness(){	return crossHairThickness;	}
	public ArithmeticExpressionDouble getCrossHairkLength(){	return crossHairLength;	}
	
}

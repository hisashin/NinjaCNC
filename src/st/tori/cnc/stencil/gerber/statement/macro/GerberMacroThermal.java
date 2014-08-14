package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroThermal extends GerberMacro {

	protected double x;
	protected double y;
	protected double outerDiameter;
	protected double innerDiameter;
	protected double gapThickness;
	
	public GerberMacroThermal(int exposure, double x, double y, double outerDiameter, double innerDiameter, double gapThickness, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.x = x;
		this.y = y;
		this.outerDiameter = outerDiameter;
		this.innerDiameter = innerDiameter;
		this.gapThickness = gapThickness;
	}
	
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	public double getOuterDiameter(){	return outerDiameter;	}
	public double getInnerDiameter(){	return innerDiameter;	}
	public double getGapThickness(){	return gapThickness;	}
	
}

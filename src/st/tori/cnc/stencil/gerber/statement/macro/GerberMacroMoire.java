package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroMoire extends GerberMacro {

	protected double x;
	protected double y;
	protected double outerDiameter;
	protected double thickness;
	protected double gap;
	protected int maximumNumberOfRings;
	protected double crossHairThickness;
	protected double crossHairLength;
	
	public GerberMacroMoire(int exposure, double x, double y, double outerDiameter, double thickness, double gap, int maximumNumberOfRings, double crossHairThickness, double crossHairLength, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.x = x;
		this.y = y;
		this.outerDiameter = outerDiameter;
		this.thickness = thickness;
		this.gap = gap;
		this.maximumNumberOfRings = maximumNumberOfRings;
		this.crossHairThickness = crossHairThickness;
		this.crossHairLength = crossHairLength;
	}
	
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	public double getOuterDiameter(){	return outerDiameter;	}
	public double getThickness(){	return thickness;	}
	public double getGap(){	return gap;	}
	public int getMaximumNumberOfRings(){	return maximumNumberOfRings;	}
	public double getCrossHairThickness(){	return crossHairThickness;	}
	public double getCrossHairkLength(){	return crossHairLength;	}
	
}

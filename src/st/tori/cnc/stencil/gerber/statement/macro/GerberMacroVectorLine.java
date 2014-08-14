package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroVectorLine extends GerberMacro {

	protected double lineWidth;
	protected double xStart;
	protected double yStart;
	protected double xEnd;
	protected double yEnd;
	
	public GerberMacroVectorLine(int exposure, double lineWidth, double xStart, double yStart, double xEnd, double yEnd, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.lineWidth = lineWidth;
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
	
	public double getLineWidth(){	return lineWidth;	}
	public double getXStart(){	return xStart;	}
	public double getYStart(){	return yStart;	}
	public double getXEnd(){	return xEnd;	}
	public double getYEnd(){	return yEnd;	}
	
}

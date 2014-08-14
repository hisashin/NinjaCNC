package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroCenterLine extends GerberMacro {

	protected double width;
	protected double height;
	protected double x;
	protected double y;
	
	public GerberMacroCenterLine(int exposure, double width, double height, double x, double y, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public double getWidth(){	return width;	}
	public double getHeight(){	return height;	}
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	
}

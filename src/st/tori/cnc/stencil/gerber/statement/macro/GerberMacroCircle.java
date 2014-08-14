package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroCircle extends GerberMacro {

	protected double diameter;
	protected double x;
	protected double y;
	
	public GerberMacroCircle(int exposure, double diameter, double x, double y, Gerber gerber) {
		super(exposure, 0.0, gerber);
		this.diameter = diameter;
		this.x = x;
		this.y = y;
	}
	
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	
}

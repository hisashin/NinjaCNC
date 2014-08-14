package st.tori.cnc.stencil.gerber.statement.aperture;

import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.parser.Gerber;


public class GerberApertureRectangle extends GerberAperture {

	protected double x;
	protected double y;
	
	public GerberApertureRectangle(int dcode, double x, double y, ApertureModifier modifier, Gerber gerber) {
		super(dcode, modifier, gerber);
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+",R";
	}


}

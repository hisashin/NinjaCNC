package st.tori.cnc.stencil.gerber.statement.aperture;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifier;


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

	@Override
	public float getStroke(PositionXYInterface lastPosition, PositionXYInterface position) {
		double theta = Math.atan2(position.getY()-lastPosition.getY(), position.getX()-lastPosition.getX());
		return (float)(Math.abs(x*Math.sin(theta))+Math.abs(y*Math.cos(theta)));
	}

}

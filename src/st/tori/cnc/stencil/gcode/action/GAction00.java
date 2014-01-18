package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G00
	Rapid positioning
	M	T	On 2- or 3-axis moves, 
	G00 (unlike G01) traditionally does not necessarily move in a single straight line 
	between start point and end point. It moves each axis at its max speed until its vector is achieved.
	Shorter vector usually finishes first (given similar axis speeds).
	This matters because it may yield a dog-leg or hockey-stick motion,
	which the programmer needs to consider depending on what obstacles are nearby,
	to avoid a crash. Some machines offer interpolated rapids as a feature for ease of programming
	(safe to assume a straight line).
 */
public class GAction00 extends GAction implements PositionXYZInterface {

	@Override
	public int getGIndex() {	return 0;	}

	public GAction00(GCode gCode) {
		super(gCode);
	}
	
	protected double x;
	protected double y;
	protected double z;
	
	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public double getX(){	return x;	}
	@Override
	public double getY(){	return y;	}
	@Override
	public double getZ(){	return z;	}

}

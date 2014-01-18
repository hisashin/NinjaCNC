package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.gcode.action.PositionXYInterface;

public class SimpleXY implements PositionXYInterface {

	private double x;
	private double y;
	
	public SimpleXY(double x, double y) {
		setX(x);
		setY(y);
	}
	
	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

}

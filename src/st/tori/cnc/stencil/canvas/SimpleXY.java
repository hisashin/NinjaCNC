package st.tori.cnc.stencil.canvas;


public class SimpleXY implements PositionXYInterface,PositionIntXYInterface {

	private double x;
	private double y;
	
	public SimpleXY(double x, double y) {
		setX(x);
		setY(y);
	}
	public SimpleXY(PositionXYInterface p) {
		setX(p.getX());
		setY(p.getY());
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

	@Override
	public int getIntX() {
		return (int)x;
	}

	@Override
	public int getIntY() {
		return (int)y;
	}

}

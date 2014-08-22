package st.tori.cnc.stencil.canvas.shape;

import java.awt.Color;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;


public class Circle implements Drawable {

	protected PositionXYInterface origin;
	protected double diameter;
	protected Color color;
	
	public Circle(PositionXYInterface origin, double diameter) {
		this.origin = origin;
		this.diameter = diameter;
	}
	
	public PositionXYInterface getOrigin(){	return origin;	}
	public double getDiameter(){	return diameter;	}
	public void setColor(Color color) {	this.color = color;	}
	public Color getColor(){	return color;	}
	
	@Override
	public PositionXYInterface[] getXYMinMax() {
		return new PositionXYInterface[]{
			new SimpleXY(origin.getX()-diameter/2, origin.getY()-diameter/2),
			new SimpleXY(origin.getX()+diameter/2, origin.getY()+diameter/2),
		};
	}

	@Override
	public void draw(DimensionController dc) {
		dc.drawCircle(this);
	}
	@Override
	public String toString() {
		return "Circle[origin=("+origin.getX()+","+origin.getY()+"),diameter="+diameter+"]";
	}
}

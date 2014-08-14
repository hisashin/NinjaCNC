package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;


public class Circle implements Drawable {

	protected PositionXYInterface origin;
	protected double diameter;
	
	public Circle(PositionXYInterface origin, double diameter) {
		this.origin = origin;
		this.diameter = diameter;
	}
	
	public PositionXYInterface getOrigin(){	return origin;	}
	public double getDiameter(){	return diameter;	}
	
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
}

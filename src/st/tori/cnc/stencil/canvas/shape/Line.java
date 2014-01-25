package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.PositionXYInterface;


public class Line extends Polyline {

	public Line(PositionXYInterface p0, PositionXYInterface p1) {
		super(new PositionXYInterface[]{p0,p1});
	}
	
}

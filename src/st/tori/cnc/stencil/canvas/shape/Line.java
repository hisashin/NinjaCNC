package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.PositionXYInterface;


public class Line extends Polyline {

	public Line(PositionXYInterface p0, PositionXYInterface p1, float stroke) {
		super(new PositionXYInterface[]{p0,p1},stroke);
	}
	
	protected PositionXYInterface p0;
	protected PositionXYInterface p1;
	
	public PositionXYInterface getP0(){	return p0;	}
	public PositionXYInterface getP1(){	return p1;	}
	
	@Override
	protected String getName() {	return "Line";	}
	
}

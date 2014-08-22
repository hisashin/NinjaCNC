package st.tori.cnc.stencil.canvas.shape;

import java.awt.Color;

import st.tori.cnc.stencil.canvas.PositionXYInterface;


public class Polygon extends Polyline {

	public Polygon(PositionXYInterface[] _xyArray, float stroke) {
		super(circle(_xyArray),stroke);
	}

	private static PositionXYInterface[] circle(PositionXYInterface[] _xyArray) {
		if(_xyArray==null||_xyArray.length<=0)return _xyArray;
		PositionXYInterface[] xyArray = new PositionXYInterface[_xyArray.length+1];
		for(int i=0;i<_xyArray.length;i++)xyArray[i] = _xyArray[i];
		xyArray[xyArray.length-1] = xyArray[0];
		return xyArray;
	}
	@Override
	protected String getName() {	return "Polygon";	}
}

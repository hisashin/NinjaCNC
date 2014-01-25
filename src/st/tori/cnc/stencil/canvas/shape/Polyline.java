package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;


public class Polyline implements Drawable {

	protected PositionXYInterface[] xyArray;
	protected float stroke;
	
	public Polyline(PositionXYInterface[] xyArray, float stroke) {
		this.xyArray = xyArray;
		this.stroke = stroke;
	}
	
	public PositionXYInterface[] getXYArray(){	return xyArray;	}
	@Override
	public PositionXYInterface[] getXYMinMax() {
		if(xyArray==null||xyArray.length<=0)return null;
		PositionXYInterface minX = null;
		PositionXYInterface minY = null;
		PositionXYInterface maxX = null;
		PositionXYInterface maxY = null;
		for(int i=0;i<xyArray.length;i++) {
			if(minX==null||xyArray[i].getX()<minX.getX())minX = xyArray[i];
			if(minY==null||xyArray[i].getY()<minY.getY())minY = xyArray[i];
			if(maxX==null||xyArray[i].getX()>maxX.getX())maxX = xyArray[i];
			if(maxY==null||xyArray[i].getY()>maxY.getY())maxY = xyArray[i];
		}
		if(minX==null||minY==null||maxX==null||maxY==null)return null;
		return new PositionXYInterface[]{
			new SimpleXY(minX.getX(), minY.getY()),
			new SimpleXY(maxX.getX(), maxY.getY()),
		};
	}

	@Override
	public void draw(DimensionController dc) {
		dc.drawPolyline(xyArray, stroke);
	}
}

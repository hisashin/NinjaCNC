package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;


public class Polyline implements Drawable {

	protected PositionXYInterface[] xyArray;
	
	public Polyline(PositionXYInterface[] xyArray) {
		this.xyArray = xyArray;
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
		if(xyArray==null||xyArray.length<=1)return;
		PositionXYInterface lastPosition = xyArray[0];
		for(int i=1;i<xyArray.length;i++) {
			dc.drawLine(lastPosition, xyArray[i]);
			lastPosition = xyArray[i];
		}
	}
}

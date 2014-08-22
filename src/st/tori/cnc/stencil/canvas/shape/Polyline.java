package st.tori.cnc.stencil.canvas.shape;

import java.awt.Color;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;


public class Polyline implements Drawable {

	protected PositionXYInterface[] xyArray;
	protected float stroke;
	protected Color color;
	
	public Polyline(PositionXYInterface[] xyArray, float stroke) {
		this.xyArray = xyArray;
		this.stroke = stroke;
	}
	
	public PositionXYInterface[] getXYArray(){	return xyArray;	}
	public float getStroke(){	return stroke;	}
	public void setColor(Color color) {	this.color = color;	}
	public Color getColor(){	return color;	}
	
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
		dc.drawPolyline(this);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer(getName()+"[");
		for(int i=0;i<xyArray.length;i++) {
			if(i>0)buf.append(",");
			PositionXYInterface position = xyArray[i];
			buf.append("("+position.getX()+","+position.getY()+")");
		}
		buf.append(",stroke="+stroke+"]");
		return buf.toString();
	}
	protected String getName() {	return "PolyLine";	}
}

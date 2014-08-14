package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;
import st.tori.cnc.stencil.util.DegUtil;


public class Rectangle implements Drawable {

	protected PositionXYInterface lowerLeftOrigin;
	protected double width;
	protected double height;
	protected double rotationAngleInDeg;
	
	public Rectangle(PositionXYInterface lowerLeftOrigin, double width, double height, double rotationAngleInDeg) {
		this.lowerLeftOrigin = lowerLeftOrigin;
		this.width = width;
		this.height = height;
		this.rotationAngleInDeg = rotationAngleInDeg;
	}
	
	public PositionXYInterface getLowerLeftOrigin(){	return lowerLeftOrigin;	}
	public double getWidth(){	return width;	}
	public double getHeight(){	return height;	}
	public double getRotationAngleInDeg(){	return rotationAngleInDeg;	}
	
	@Override
	public PositionXYInterface[] getXYMinMax() {
		double x0 = lowerLeftOrigin.getX();
		double x1 = x0 + width*DegUtil.cos(rotationAngleInDeg);
		double x2 = x0 + height*DegUtil.cos(90+rotationAngleInDeg);
		double x3 = x2 + width*DegUtil.cos(rotationAngleInDeg);
		double y0 = lowerLeftOrigin.getY();
		double y1 = y0 + width*DegUtil.sin(rotationAngleInDeg);
		double y2 = y0 + height*DegUtil.sin(90+rotationAngleInDeg);
		double y3 = y2 + width*DegUtil.sin(rotationAngleInDeg);
		
		double minX = Math.min(Math.min(Math.min(x0, x1), x2), x3);
		double minY = Math.min(Math.min(Math.min(y0, y1), y2), y3);
		double maxX = Math.max(Math.max(Math.max(x0, x1), x2), x3);
		double maxY = Math.max(Math.max(Math.max(y0, y1), y2), y3);
		
		return new PositionXYInterface[]{
			new SimpleXY(minX, minY),
			new SimpleXY(maxX, maxY),
		};
	}

	@Override
	public void draw(DimensionController dc) {
		dc.drawRectangle(this);
	}
}

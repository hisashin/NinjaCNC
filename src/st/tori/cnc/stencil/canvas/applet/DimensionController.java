package st.tori.cnc.stencil.canvas.applet;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.shape.Circle;
import st.tori.cnc.stencil.canvas.shape.Polyline;
import st.tori.cnc.stencil.canvas.shape.Rectangle;

public class DimensionController {

	private static final float MIN_STROKE = 0.2f;
	private boolean debug;
	private Applet applet;
	private Graphics graphics;
	private PositionXYInterface[] xyMinMax;
	private double xWidth;
	private double yHeight;
	
	private double xMergin;
	private double yMergin;
	private double xyWidth;
	private double xyHeight;
	private double ratio;
	
	private Color currentColor = null;
	
	public DimensionController(boolean debug, Applet applet, Graphics graphics, PositionXYInterface[] xyMinMax) {
		this.debug = debug;
		this.applet = applet;
		this.graphics = graphics;
		this.xyMinMax = xyMinMax;
		Dimension dimension = applet.getSize();
		this.xWidth = dimension.getWidth();
		this.yHeight = dimension.getHeight();
		double minMergin = Math.min(yHeight, xWidth)*0.1;
		if(xyMinMax!=null) {
			this.xyWidth = xyMinMax[1].getX()-xyMinMax[0].getX();
			this.xyHeight = xyMinMax[1].getY()-xyMinMax[0].getY();
			if(xyHeight/xyWidth > (yHeight-2*minMergin)/(xWidth-2*minMergin)) {
				//fill Y
				yMergin = minMergin;
				ratio = (yHeight-2*minMergin)/xyHeight;
				xMergin = (xWidth - ratio*xyWidth)/2;
			}else{
				//fill X
				xMergin = minMergin;
				ratio = (xWidth-2*minMergin)/xyWidth;
				yMergin = (yHeight - ratio*xyHeight)/2;
			}
		}
	}
	@Override
	public String toString() {
		return "DimensionController[dimension.getWidth()="+applet.getSize().getWidth()+",dimension.getHeight()="+applet.getSize().getHeight()+",xyMin=("+xyMinMax[0].getX()+","+xyMinMax[0].getY()+"),xyMax=("+xyMinMax[1].getX()+","+xyMinMax[1].getY()+"),xyWidth="+xyWidth+",xyHeight="+xyHeight+"]";
	}
	
	public void drawPolyline(Polyline obj) {
		PositionXYInterface[] xyArray = obj.getXYArray();
		if(xyArray==null||xyArray.length<=1)return;
		float stroke = (float)Math.max(obj.getStroke()*ratio,MIN_STROKE);
		if(stroke>0)
			((Graphics2D)graphics).setStroke(new BasicStroke(stroke));
		PositionXYInterface lastPosition = xyArray[0];
		for(int i=1;i<xyArray.length;i++) {
			graphics.drawLine(getXtoShow(lastPosition.getX()), getYtoShow(lastPosition.getY()), 
					getXtoShow(xyArray[i].getX()), getYtoShow(xyArray[i].getY()));
			//System.out.println("graphics.drawLine[("+getXtoShow(lastPosition.getX())+","+getYtoShow(lastPosition.getY())+") to ("+getXtoShow(xyArray[i].getX())+","+getYtoShow(xyArray[i].getY())+")]");
			lastPosition = xyArray[i];
		}
	}
	public void drawCircle(Circle obj) {
		PositionXYInterface origin = obj.getOrigin();
		double diameter = obj.getDiameter();
		graphics.fillOval(getXtoShow(origin.getX()-diameter/2),getYtoShow(origin.getY()+diameter/2), getXtoShow(diameter), getYtoShow(diameter));
		//System.out.println("graphics.fillOval("+getXtoShow(origin.getX()-diameter/2)+","+getYtoShow(origin.getY()+diameter/2)+","+getXtoShow(diameter)+","+getYtoShow(diameter)+")");
	}
	public void drawRectangle(Rectangle obj) {
		PositionXYInterface lowerLeftOrigin = obj.getLowerLeftOrigin();
		double width = obj.getWidth();
		double height = obj.getHeight();
		double rotationAngleInDeg = obj.getRotationAngleInDeg();
		graphics.fillRect(getXtoShow(lowerLeftOrigin.getX()),getYtoShow(lowerLeftOrigin.getY()), getXtoShow(width), getYtoShow(height));
		//System.out.println("graphics.fillRect("+getXtoShow(lowerLeftOrigin.getX())+","+getYtoShow(lowerLeftOrigin.getY())+","+getXtoShow(width)+","+getYtoShow(height)+")");
	}
	
	private boolean flipVertical = false;
	private boolean flipHolizontal = false;

	public void flipVertical() {	flipVertical = !flipVertical;	}
	public void flipHolizontal() {	flipHolizontal = !flipHolizontal;	}
	
	private int getXtoShow(double x) {
		if(flipHolizontal)
			return (int)(xWidth - xMergin - (x - xyMinMax[0].getX())*ratio);
		else
			return (int)(xMergin + (x - xyMinMax[0].getX())*ratio);
	}
	private int getYtoShow(double y) {
		if(flipVertical)
			return (int)(yMergin + (y - xyMinMax[0].getY())*ratio);
		else
			return (int)(yHeight - yMergin - (y - xyMinMax[0].getY())*ratio);
	}
	
	public void _paint(Applet applet, Graphics g) {

		Dimension size = applet.getSize();

		g.setColor(Color.blue);
		g.fillRect(0, 0, size.width - 1, size.height - 1);

		Color color = new Color(127, 0, 63);
		g.setColor(color);
		g.drawString("abcdefghijklmn", 10, 50);

		g.drawLine(5, 50, 100, 50);
		g.drawLine(10, 10, 100, 50);

		g.setColor(Color.red);
		g.drawLine(15, 70, 120, 20);
		g.drawRect(10, 10, 100, 50);

		g.setColor(Color.red);
		g.drawRoundRect(20, 20, 120, 70, 5, 10);

		g.drawOval(10, 10, 100, 50);

		g.setColor(Color.red);
		g.drawArc(40, 30, 100, 70, 45, 180);

		g.setColor(Color.blue);
		g.drawRect(40, 30, 100, 70);
		g.drawLine(40, 100, 140, 30);
		g.drawLine(900, 30, 900, 100);
		g.drawLine(40, 65, 140, 65);
		g.fillOval(10, 10, 100, 50);

		g.setColor(Color.red);
		g.fillArc(40, 30, 100, 70, 45, 135);

		int xPoints[] = { 10, 50, 20, 120 };
		int yPoints[] = { 80, 50, 20, 90 };

		g.drawPolyline(xPoints, yPoints, 4);

		int xPoints1[] = { 10, 50, 20, 120 };
		int yPoints1[] = { 80, 50, 20, 90 };

		g.drawPolygon(xPoints1, yPoints1, 4);
		int xPoints2[] = { 10, 90, 75, 20 };
		int yPoints2[] = { 20, 100, 25, 90 };

		g.fillPolygon(xPoints2, yPoints2, 4);

		g.setColor(Color.blue);

		int xPoints3[] = { 120, 140, 90 };
		int yPoints3[] = { 20, 70, 30 };

		g.fillPolygon(xPoints3, yPoints3, 3);

	}
}

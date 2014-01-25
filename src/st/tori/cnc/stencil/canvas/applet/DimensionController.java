package st.tori.cnc.stencil.canvas.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;

public class DimensionController {

	private static final int DIMENSION_WIDTH		 = 1400;
	private static final int DIMENSION_HEIGHT		 = 800;
	private static final int DIMENSION_MIN_MERGIN	 = 50;
	
	public static final Dimension DIMEINSION = new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT);
	
	private boolean debug;
	private Applet applet;
	private Graphics graphics;
	private PositionXYInterface[] xyMinMax;
	private double xMergin;
	private double yMergin;
	private double xyWidth;
	private double xyHeight;
	
	public DimensionController(boolean debug, Applet applet, Graphics graphics, PositionXYInterface[] xyMinMax) {
		this.debug = debug;
		this.applet = applet;
		this.graphics = graphics;
		this.xyMinMax = xyMinMax;
		if(xyMinMax!=null) {
			this.xyWidth = xyMinMax[1].getX()-xyMinMax[0].getX();
			this.xyHeight = xyMinMax[1].getY()-xyMinMax[0].getY();
			if(xyHeight/xyWidth > (DIMENSION_HEIGHT-2*DIMENSION_MIN_MERGIN)/(DIMENSION_WIDTH-2*DIMENSION_MIN_MERGIN)) {
				//fill Y
				yMergin = DIMENSION_MIN_MERGIN;
				xMergin = (DIMENSION_WIDTH - (DIMENSION_HEIGHT-2*DIMENSION_MIN_MERGIN)/xyHeight*xyWidth)/2;
			}else{
				//fill X
				xMergin = DIMENSION_MIN_MERGIN;
				yMergin = (DIMENSION_HEIGHT - (DIMENSION_WIDTH-2*DIMENSION_MIN_MERGIN)/xyWidth*xyHeight)/2;
			}
		}
		if(debug) {
			//drawMerginSquare();
		}
	}
	private void drawMerginSquare() {
		drawLine(new SimpleXY(xyMinMax[0].getX(), xyMinMax[0].getY()), new SimpleXY(xyMinMax[1].getX(), xyMinMax[0].getY()));
		drawLine(new SimpleXY(xyMinMax[1].getX(), xyMinMax[0].getY()), new SimpleXY(xyMinMax[1].getX(), xyMinMax[1].getY()));
		drawLine(new SimpleXY(xyMinMax[1].getX(), xyMinMax[1].getY()), new SimpleXY(xyMinMax[0].getX(), xyMinMax[1].getY()));
		drawLine(new SimpleXY(xyMinMax[0].getX(), xyMinMax[1].getY()), new SimpleXY(xyMinMax[0].getX(), xyMinMax[0].getY()));
	}
	
	public void drawLine(PositionXYInterface p0, PositionXYInterface p1) {
		graphics.drawLine(getXtoShow(p0.getX()), getYtoShow(p0.getY()), getXtoShow(p1.getX()), getYtoShow(p1.getY()));
	}
	private int getXtoShow(double x) {
		return (int)(xMergin + (x - xyMinMax[0].getX())/xyWidth*(DIMENSION_WIDTH-2*xMergin));
	}
	private int getYtoShow(double y) {
		return (int)(DIMENSION_HEIGHT - yMergin - (y - xyMinMax[0].getY())/xyHeight*(DIMENSION_HEIGHT-2*yMergin));
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

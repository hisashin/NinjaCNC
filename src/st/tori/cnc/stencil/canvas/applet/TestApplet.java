package st.tori.cnc.stencil.canvas.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class TestApplet extends Applet {

	public void paint(Graphics g) {
		Dimension size = getSize();

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

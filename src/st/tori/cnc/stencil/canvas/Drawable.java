package st.tori.cnc.stencil.canvas;

import java.awt.Color;

import st.tori.cnc.stencil.canvas.applet.DimensionController;


public interface Drawable {

	void setColor(Color color);
	Color getColor();
	
	PositionXYInterface[] getXYMinMax();
	
	void draw(DimensionController dc);

}

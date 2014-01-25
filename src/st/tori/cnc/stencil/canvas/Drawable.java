package st.tori.cnc.stencil.canvas;

import st.tori.cnc.stencil.canvas.applet.DimensionController;


public interface Drawable {

	PositionXYInterface[] getXYMinMax();
	
	void draw(DimensionController dc);

}

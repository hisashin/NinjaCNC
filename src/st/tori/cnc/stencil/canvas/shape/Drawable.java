package st.tori.cnc.stencil.canvas.shape;

import st.tori.cnc.stencil.gcode.action.PositionXYInterface;

public interface Drawable {

	PositionXYInterface[] getXYMinMax();

}

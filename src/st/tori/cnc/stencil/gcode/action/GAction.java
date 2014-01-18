package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.SpeedInterface;
import st.tori.cnc.stencil.util.NumberUtil;

public abstract class GAction implements ActionInterface {
	
	protected abstract int getGIndex();
	
	public GAction(GCode gCode) {
		inherit(gCode);
	}
	protected void inherit(GCode gCode) {
		if(gCode==null)return;
		PositionXYZInterface lastPosition = gCode.getLastPosition();
		if(lastPosition != null && this instanceof PositionXYZInterface) {
			((PositionXYZInterface)this).setX(lastPosition.getX());
			((PositionXYZInterface)this).setY(lastPosition.getY());
			((PositionXYZInterface)this).setZ(lastPosition.getZ());
		}
		SpeedInterface lastSpeed = gCode.getLastSpeed();
		if(lastSpeed != null && this instanceof SpeedInterface) {
			((SpeedInterface)this).setF(lastSpeed.getF());
		}
	}
	
	@Override
	public final String getSimpleName() {
		return "G"+NumberUtil.toString(getGIndex(), 2);
	}
	
	/*
	 * if true,no return added on its head so that it can be find on first line easily
	 */
	public boolean isFundamental() {	return false;	}
	
}

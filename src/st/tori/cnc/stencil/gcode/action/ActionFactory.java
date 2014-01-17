package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.exception.InvalidIndexException;
import st.tori.cnc.stencil.gcode.parser.SpeedInterface;

public class ActionFactory {

	public static GAction createGAction(int gIndex, double lastSpeed) throws InvalidIndexException {
		GAction action;
		if(gIndex==0)
			action = new GAction00();
		else if(gIndex==1)
			action = new GAction01();
		else if(gIndex==21)
			action = new GAction21();
		else if(gIndex>=54&&gIndex<=59)
			action = new GAction54to59(gIndex);
		else if(gIndex==61)
			action = new GAction61();
		else if(gIndex==90)
			action = new GAction90();
		else
			throw new InvalidIndexException("G",gIndex);
		if(lastSpeed>0&&action instanceof SpeedInterface)
			((SpeedInterface)action).setF(lastSpeed);
		return action;
	}
	
	public static MAction createMAction(int mIndex) throws InvalidIndexException {
		if(mIndex==3)return new MAction03();
		if(mIndex==30)return new MAction30();
		throw new InvalidIndexException("M",mIndex);
	}

}

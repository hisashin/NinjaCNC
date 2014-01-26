package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G64
	Default cutting mode (cancel exact stop check mode)	
	M	T	
	Cancels G61.
 */
public class GAction64 extends GAction {

	@Override
	public int getGIndex() {	return 64;	}

	public GAction64(GCode gCode) {
		super(gCode);
	}

}

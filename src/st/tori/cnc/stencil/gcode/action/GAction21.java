package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G21
	Programming in millimeters (mm)
	M	T
	Prevalent worldwide. However, in the global marketplace,
	competence with both G20 and G21 always stands some chance of being necessary at any time.
 */
public class GAction21 extends GAction {

	@Override
	public int getGIndex() {	return 21;	}
	@Override
	public boolean isFundamental() {	return true;	}

	public GAction21(GCode gCode) {
		super(gCode);
	}

}

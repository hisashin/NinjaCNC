package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G54 to G59
	Work coordinate systems (WCSs)
	M	T
	Have largely replaced position register (G50 and G92).
	Each tuple of axis offsets relates program zero directly to machine zero.
	Standard is 6 tuples (G54 to G59), with optional extensibility to 48 more via G54.1 P1 to P48.
 */
public class GAction54to59 extends GAction {

	@Override
	public int getGIndex() {	return gIndex;	}

	public GAction54to59(GCode gCode) {
		super(gCode);
	}

	protected int gIndex = -1;
	
	public void setGIndex(int gIndex) {
		this.gIndex = gIndex;
	}

}

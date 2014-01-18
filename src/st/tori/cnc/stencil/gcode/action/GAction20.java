package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G20
	Programming in inches
	M	T
	Somewhat uncommon except in USA and (to lesser extent) Canada and UK.
	However, in the global marketplace, competence with both G20 and G21 
	always stands some chance of being necessary at any time. 
	The usual minimum increment in G20 is one ten-thousandth of an inch (0.0001"), 
	which is a larger distance than the usual minimum increment in G21 
	(one thousandth of a millimeter, .001 mm, that is, one micrometre). 
	This physical difference sometimes favors G21 programming.
 */
public class GAction20 extends GAction {

	@Override
	public int getGIndex() {	return 20;	}
	@Override
	public boolean isFundamental() {	return true;	}

	public GAction20(GCode gCode) {
		super(gCode);
	}

}

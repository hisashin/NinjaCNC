package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.GCode;

/*
	G61
	Exact stop check, modal
	M	T
	Can be canceled with G64. The non-modal version is G09.
 */
public class GAction61 extends GAction {

	@Override
	public int getGIndex() {	return 61;	}
	@Override
	public boolean isFundamental() {	return true;	}

	public GAction61(GCode gCode) {
		super(gCode);
	}

}

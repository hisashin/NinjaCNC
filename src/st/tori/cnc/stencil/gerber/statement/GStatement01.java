package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set the interpolation mode to liner
 */
public class GStatement01 extends GStatement {

	@Override
	public int getGIndex() {	return 1;	}

	public GStatement01(Gerber gerber) {
		super(gerber);
	}

}

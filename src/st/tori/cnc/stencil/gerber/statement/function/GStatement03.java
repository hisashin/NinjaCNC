package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set the interpolation mode to 'Counterclockwise circular interpolation'
 */
public class GStatement03 extends GStatement {

	@Override
	public int getGIndex() {	return 3;	}

	public GStatement03(Gerber gerber) {
		super(gerber);
	}

}

package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Ignore data block
 */
public class GStatement04 extends GStatement {

	@Override
	public int getGIndex() {	return 4;	}

	public GStatement04(Gerber gerber) {
		super(gerber);
	}

}

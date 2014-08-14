package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set region mode on
 */
public class GStatement36 extends GStatement {

	@Override
	public int getGIndex() {	return 36;	}

	public GStatement36(Gerber gerber) {
		super(gerber);
	}

}

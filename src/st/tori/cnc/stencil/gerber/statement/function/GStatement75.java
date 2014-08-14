package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set quadrant mode to 'Multi quadrant'
 */
public class GStatement75 extends GStatement {

	@Override
	public int getGIndex() {	return 75;	}

	public GStatement75(Gerber gerber) {
		super(gerber);
	}

}

package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Interpolate operation code
 */
public class DStatement01 extends DStatement {

	@Override
	protected int getDIndex() {	return 1;	}

	public DStatement01(Gerber gerber) {
		super(gerber);
	}

}

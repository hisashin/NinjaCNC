package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Flash operation code
 */
public class DStatement03 extends DStatement {

	@Override
	protected int getDIndex() {	return 1;	}

	public DStatement03(Gerber gerber) {
		super(gerber);
	}

}

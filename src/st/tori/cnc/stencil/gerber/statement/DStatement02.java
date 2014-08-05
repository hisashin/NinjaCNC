package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Move operation code
 */
public class DStatement02 extends DStatement {

	@Override
	protected int getDIndex() {	return 1;	}

	public DStatement02(Gerber gerber) {
		super(gerber);
	}

}

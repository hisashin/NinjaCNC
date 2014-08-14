package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set the current aperture
 */
public class DStatement10orHigher extends DStatement {

	@Override
	protected int getDIndex() {	return 1;	}

	public DStatement10orHigher(Gerber gerber) {
		super(gerber);
	}

}

package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GStatement03 extends GStatement {

	@Override
	public int getGIndex() {	return 3;	}

	public GStatement03(Gerber gerber) {
		super(gerber);
	}

}

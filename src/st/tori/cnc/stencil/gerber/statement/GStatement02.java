package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GStatement02 extends GStatement {

	@Override
	public int getGIndex() {	return 2;	}

	public GStatement02(Gerber gerber) {
		super(gerber);
	}

}

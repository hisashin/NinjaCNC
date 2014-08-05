package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Set quadrant mode to 'Single quadrant'
 */
public class GStatement74 extends GStatement {

	@Override
	public int getGIndex() {	return 74;	}

	public GStatement74(Gerber gerber) {
		super(gerber);
	}

}

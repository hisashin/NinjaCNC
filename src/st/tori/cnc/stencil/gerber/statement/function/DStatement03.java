package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Flash operation code
 */
public class DStatement03 extends DStatement {

	@Override
	protected int getDIndex() {	return 3;	}

	public DStatement03(PositionXYInterface position, Gerber gerber) {
		super(position, gerber);
	}

}

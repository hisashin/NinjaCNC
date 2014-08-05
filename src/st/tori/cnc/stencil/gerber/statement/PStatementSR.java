package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Step and Repeat
 */
public class PStatementSR extends PStatement {

	@Override
	protected String getParameterCode() {	return "SR";	}

	public PStatementSR(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
	}

}

package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Mode(inch or mm)
 */
public class PStatementMO extends PStatement {

	@Override
	protected String getParameterCode() {	return "MO";	}

	public PStatementMO(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
	}

}

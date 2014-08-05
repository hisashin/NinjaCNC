package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Level Polarity
 */
public class PStatementLP extends PStatement {

	@Override
	protected String getParameterCode() {	return "LP";	}

	public PStatementLP(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
	}

}

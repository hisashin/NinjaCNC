package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Image Polarity
 */
public class PStatementIP extends PStatement {

	@Override
	protected String getParameterCode() {	return "IP";	}

	public PStatementIP(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
	}

}

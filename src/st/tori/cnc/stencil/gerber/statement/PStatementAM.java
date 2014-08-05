package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Aperture Macro
 */
public class PStatementAM extends PStatement {

	@Override
	protected String getParameterCode() {	return "AM";	}

	public PStatementAM(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
	}

}

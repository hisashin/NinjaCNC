package st.tori.cnc.stencil.gerber.statement.parameter;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Aperture Definition
 */
public class PStatementAD extends PStatement {

	@Override
	protected String getParameterCode() {	return "AD";	}

	public PStatementAD(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);

		throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

}

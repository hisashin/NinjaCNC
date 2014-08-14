package st.tori.cnc.stencil.gerber.statement.parameter;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.UnsupportedStatementInterface;

/*
 * Attributes
 */
public class PStatementTD extends PStatement implements UnsupportedStatementInterface {

	@Override
	protected String getParameterCode() {	return "TD";	}

	public PStatementTD(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		//throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

}

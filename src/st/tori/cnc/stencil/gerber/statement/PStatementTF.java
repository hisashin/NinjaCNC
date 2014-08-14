package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Attributes
 */
public class PStatementTF extends PStatement implements UnsupportedStatementInterface {

	@Override
	protected String getParameterCode() {	return "TF";	}

	public PStatementTF(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		//throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

}

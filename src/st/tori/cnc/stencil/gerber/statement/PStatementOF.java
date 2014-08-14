package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Attributes
 */
public class PStatementOF extends PStatement {

	@Override
	protected String getParameterCode() {	return "OF";	}

	public PStatementOF(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		System.out.println("Ignore %"+getParameterCode()+modifiers);
		//throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

}

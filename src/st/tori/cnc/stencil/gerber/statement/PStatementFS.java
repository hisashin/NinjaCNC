package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Format Specification
 */
public class PStatementFS extends PStatement {

	@Override
	protected String getParameterCode() {	return "FS";	}

	public PStatementFS(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers,gerber);
		try {
			
		}catch(Exception e){
			throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
		}
	}

}

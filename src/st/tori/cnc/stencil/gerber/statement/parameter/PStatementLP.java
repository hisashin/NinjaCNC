package st.tori.cnc.stencil.gerber.statement.parameter;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.GerberLevel.GERBER_LEVEL_POLARITY;

/*
 * Level Polarity
 */
public class PStatementLP extends PStatement {

	@Override
	protected String getParameterCode() {	return "LP";	}

	public PStatementLP(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		if(modifiers!=null) {
			if(modifiers.startsWith("C")) {
				polarity = GERBER_LEVEL_POLARITY.CLEAR;
				return;
			}else if(modifiers.startsWith("D")) {
				polarity = GERBER_LEVEL_POLARITY.DARK;
				return;
			}
		}
		throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}
	
	public GERBER_LEVEL_POLARITY polarity;

}

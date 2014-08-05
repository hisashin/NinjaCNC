package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.Gerber.UNIT_MODE;

/*
 * Mode(inch or mm)
 */
public class PStatementMO extends PStatement {

	@Override
	protected String getParameterCode() {	return "MO";	}

	public PStatementMO(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		if(modifiers!=null) {
			if(modifiers.startsWith("IN")) {
				unitMode = UNIT_MODE.INCHES;
				return;
			}else if(modifiers.startsWith("MM")) {
				unitMode = UNIT_MODE.MILLIMETERS;
				return;
			}
		}
		throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

	public UNIT_MODE unitMode;
}

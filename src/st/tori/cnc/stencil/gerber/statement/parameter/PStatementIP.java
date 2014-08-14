package st.tori.cnc.stencil.gerber.statement.parameter;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.Gerber.IMAGE_POLARITY;

/*
 * Image Polarity
 */
public class PStatementIP extends PStatement {

	@Override
	protected String getParameterCode() {	return "IP";	}

	public PStatementIP(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		if(modifiers!=null) {
			if(modifiers.startsWith("POS")) {
				imagePolarity = IMAGE_POLARITY.POSITIVE;
				return;
			}else if(modifiers.startsWith("NEG")) {
				imagePolarity = IMAGE_POLARITY.NEGATIVE;
				return;
			}
		}
		throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}

	public IMAGE_POLARITY imagePolarity;
}

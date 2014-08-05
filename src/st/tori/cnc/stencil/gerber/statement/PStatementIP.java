package st.tori.cnc.stencil.gerber.statement;

/*
 * Image Polarity
 */
public class PStatementIP extends PStatement {

	@Override
	protected String getParameterCode() {	return "IP";	}

	public PStatementIP(String modifiers) {
		super(modifiers);
	}

}

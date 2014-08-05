package st.tori.cnc.stencil.gerber.statement;

/*
 * Step and Repeat
 */
public class PStatementSR extends PStatement {

	@Override
	protected String getParameterCode() {	return "SR";	}

	public PStatementSR(String modifiers) {
		super(modifiers);
	}

}

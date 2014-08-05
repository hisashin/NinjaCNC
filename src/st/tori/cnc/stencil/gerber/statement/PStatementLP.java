package st.tori.cnc.stencil.gerber.statement;

/*
 * Level Polarity
 */
public class PStatementLP extends PStatement {

	@Override
	protected String getParameterCode() {	return "LP";	}

	public PStatementLP(String modifiers) {
		super(modifiers);
	}

}

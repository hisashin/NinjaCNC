package st.tori.cnc.stencil.gerber.statement;

/*
 * Mode(inch or mm)
 */
public class PStatementMO extends PStatement {

	@Override
	protected String getParameterCode() {	return "MO";	}

	public PStatementMO(String modifiers) {
		super(modifiers);
	}

}

package st.tori.cnc.stencil.gerber.statement;

/*
 * Aperture Macro
 */
public class PStatementAM extends PStatement {

	@Override
	protected String getParameterCode() {	return "AM";	}

	public PStatementAM(String modifiers) {
		super(modifiers);
	}

}

package st.tori.cnc.stencil.gerber.statement;

/*
 * Aperture Definition
 */
public class PStatementAD extends PStatement {

	@Override
	protected String getParameterCode() {	return "AD";	}

	public PStatementAD(String modifiers) {
		super(modifiers);
	}

}

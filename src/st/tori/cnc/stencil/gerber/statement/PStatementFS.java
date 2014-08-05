package st.tori.cnc.stencil.gerber.statement;

/*
 * Format Specification
 */
public class PStatementFS extends PStatement {

	@Override
	protected String getParameterCode() {	return "FS";	}

	public PStatementFS(String modifiers) {
		super(modifiers);
	}

}

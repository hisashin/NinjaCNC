package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.statement.StatementInterface;


public class Comment implements StatementInterface {

	private String comment;
	
	public Comment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public final String getSimpleName() {
		return "G40 "+comment+"*";
	}

}

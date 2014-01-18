package st.tori.cnc.stencil.gcode.action;


public class Comment implements ActionInterface {

	private String comment;
	
	public Comment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public final String getSimpleName() {
		return "("+comment+")";
	}

}

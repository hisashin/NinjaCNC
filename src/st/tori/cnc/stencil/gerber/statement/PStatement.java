package st.tori.cnc.stencil.gerber.statement;



public abstract class PStatement implements StatementInterface {

	protected abstract String getParameterCode();
	
	protected String modifiers;
	public PStatement(String modifiers) {
		this.modifiers = modifiers;
	}
	protected String getModifiers(){
		return modifiers;
	}

	@Override
	public final String getSimpleName() {
		return "%"+getParameterCode()+getModifiers()+"%";
	}

}

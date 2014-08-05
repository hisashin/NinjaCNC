package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;



public abstract class PStatement implements StatementInterface {

	protected abstract String getParameterCode();
	
	protected String modifiers;
	
	public PStatement(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		this.modifiers = modifiers;
		inherit(gerber);
	}
	protected void inherit(Gerber gerber) {
		if(gerber==null)return;
		PositionXYInterface lastPosition = gerber.getLastPosition();
		if(lastPosition != null && this instanceof PositionXYInterface) {
			((PositionXYInterface)this).setX(lastPosition.getX());
			((PositionXYInterface)this).setY(lastPosition.getY());
		}
	}
	
	protected String getModifiers(){
		return modifiers;
	}

	@Override
	public final String getSimpleName() {
		return "%"+getParameterCode()+getModifiers()+"%";
	}

}

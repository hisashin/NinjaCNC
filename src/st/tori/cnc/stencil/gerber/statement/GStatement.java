package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.util.NumberUtil;

public abstract class GStatement implements StatementInterface {

	public abstract int getGIndex();
	
	public GStatement(Gerber gerber) {
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
	
	@Override
	public final String getSimpleName() {
		return "G"+NumberUtil.toString(getGIndex(), 2);
	}

}

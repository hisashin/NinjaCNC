package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.util.NumberUtil;

public abstract class GStatement implements StatementInterface {

	public abstract int getGIndex();
	
	public GStatement(Gerber gerber) {
		inherit(gerber);
	}
	protected void inherit(Gerber gerber) {
		if(gerber==null)return;
		PositionXYZInterface lastPosition = gerber.getLastPosition();
		if(lastPosition != null && this instanceof PositionXYZInterface) {
			((PositionXYZInterface)this).setX(lastPosition.getX());
			((PositionXYZInterface)this).setY(lastPosition.getY());
			((PositionXYZInterface)this).setZ(lastPosition.getZ());
		}
	}
	
	@Override
	public final String getSimpleName() {
		return "G"+NumberUtil.toString(getGIndex(), 2);
	}

}

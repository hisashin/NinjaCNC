package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.util.NumberUtil;

public abstract class DStatement implements StatementInterface {

	protected abstract int getDIndex();

	public DStatement(Gerber gerber) {
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
		return "D"+NumberUtil.toString(getDIndex(), 2)+"*";
	}

}

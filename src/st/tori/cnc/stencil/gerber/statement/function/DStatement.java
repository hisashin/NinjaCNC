package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.util.NumberUtil;

public abstract class DStatement implements StatementInterface,PositionXYInterface {

	protected abstract int getDIndex();
	protected double x;
	protected double y;

	public DStatement(PositionXYInterface position, Gerber gerber) {
		if(position==null)return;
		setX(position.getX());
		setY(position.getY());
	}

	public void setX(double x){	this.x = x;	}
	public void setY(double y){	this.y = y;	}
	
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	
	@Override
	public final String getSimpleName() {
		return "D"+NumberUtil.toString(getDIndex(), 2)+"*";
	}

}

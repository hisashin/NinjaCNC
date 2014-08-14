package st.tori.cnc.stencil.gerber.aperture;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;

public abstract class GerberAperture implements StatementInterface {

	protected int dcode;
	protected ApertureModifier modifier;
	
	public GerberAperture(int dcode, ApertureModifier modifier, Gerber gerber) {
		this.dcode = dcode;
		this.modifier = modifier;
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

	public int getDcode(){	return dcode;	}
	public ApertureModifier getModifier(){	return modifier;	}
	
}

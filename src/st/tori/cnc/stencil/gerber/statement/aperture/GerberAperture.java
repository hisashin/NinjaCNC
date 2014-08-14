package st.tori.cnc.stencil.gerber.statement.aperture;

import java.util.Collection;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifier;

public abstract class GerberAperture implements StatementInterface {

	public abstract Collection<Drawable> createDrawableCollection(PositionXYInterface origin);
	public abstract float getStroke(PositionXYInterface lastPosition, PositionXYInterface position);

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

package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public abstract class GerberMacro {

	protected boolean exposure;
	protected double rotationAngle;
	
	public GerberMacro(int exposure, double rotationAngle, Gerber gerber) {
		inherit(gerber);
		this.exposure = (exposure>=1);
		this.rotationAngle = rotationAngle;
	}
	protected void inherit(Gerber gerber) {
		if(gerber==null)return;
		PositionXYInterface lastPosition = gerber.getLastPosition();
		if(lastPosition != null && this instanceof PositionXYInterface) {
			((PositionXYInterface)this).setX(lastPosition.getX());
			((PositionXYInterface)this).setY(lastPosition.getY());
		}
	}
	
	public boolean getExposure(){	return exposure;	}
	public double getRotationAngle(){	return rotationAngle;	}

}

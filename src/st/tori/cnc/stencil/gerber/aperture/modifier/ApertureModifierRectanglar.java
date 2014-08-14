package st.tori.cnc.stencil.gerber.aperture.modifier;


public class ApertureModifierRectanglar implements ApertureModifier {
	private double xSize;
	private double ySize;
	public ApertureModifierRectanglar(double xSize, double ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
	}
	public double getXSize(){	return xSize;	}
	public double getYSize(){	return ySize;	}
	@Override
	public String toString() {
		return "ApertureModifierRectanglar("+xSize+"x"+ySize+")";
	}
}
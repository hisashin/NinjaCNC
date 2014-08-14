package st.tori.cnc.stencil.gerber.statement.aperture.modifier;


public class ApertureModifierHole implements ApertureModifier {
	private double diameter;
	public ApertureModifierHole(double diameter) {
		this.diameter = diameter;
	}
	public double getDiameter() {	return diameter;	}
	@Override
	public String toString() {
		return "ApertureModifierHole(diameter="+diameter+")";
	}
}
package st.tori.cnc.stencil.gerber.statement.aperture;

import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.parser.Gerber;


public class GerberApertureObround extends GerberApertureRectangle {
	
	public GerberApertureObround(int dcode, double x, double y, ApertureModifier modifier, Gerber gerber) {
		super(dcode, x, y, modifier, gerber);
	}
	
	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+",O";
	}


}

package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroLowerLeftLine extends GerberMacroCenterLine {

	public GerberMacroLowerLeftLine(int exposure, double width, double height, double x, double y, double rotationAngle, Gerber gerber) {
		super(exposure, width, height, x, y, rotationAngle, gerber);
	}
	
}

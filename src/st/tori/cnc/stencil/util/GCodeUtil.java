package st.tori.cnc.stencil.util;

import st.tori.cnc.stencil.gcode.drill.DrillInterface;
import st.tori.cnc.stencil.gcode.exception.CannotDetectCornerHolesException;
import st.tori.cnc.stencil.gcode.parser.GCode;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GCodeUtil {

	/*
	 * Create GCode from Gerber
	 */
	public static GCode createGCodeForSolderStencil(Gerber creamGerber, DrillInterface drill) {
		//TODO
		return null;
	}

	/*
	 * Detect 4 corner holes and deepen it to increase proofreading precision
	 */
	public static GCode deppDrilling4CornerHoles(GCode gCode) throws CannotDetectCornerHolesException {
		//TODO
		return gCode;
	}
	
	/*
	 * Change drill setting only for outline so that thick cheap drill can be used to cut.
	 * Maybe ignore large drill holes.
	 */
	public static GCode changeDrillForOutline(GCode gCode) {
		//TODO
		return gCode;
	}
	
}

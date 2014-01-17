package st.tori.cnc.stencil;

import java.io.File;

import st.tori.cnc.stencil.gcode.action.GCode;
import st.tori.cnc.stencil.gcode.exception.GCodeException;
import st.tori.cnc.stencil.gcode.parser.GCodeParser;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Gerber!");
		//GerberParser gbParser = new GerberParser();
		//gbParser.parse(new File("gerber/levistone_tcream.gtp"));
		try {
			GCodeParser gcParser = new GCodeParser();
			GCode codeNcd = gcParser.parse(new File("gerber/levistone_tcream.ncd"));
			GCode codeSample = createSample();
		} catch (GCodeException e) {
			e.printStackTrace();
		}
	}

	private static GCode createSample() {
		GCode code = new GCode();
		return code;
	}
	

}

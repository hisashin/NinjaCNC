package st.tori.cnc.stencil;

import java.io.File;

import st.tori.cnc.stencil.gcode.exception.GCodeException;
import st.tori.cnc.stencil.gcode.parser.GCodeParser;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Gerber!");
		//GerberParser gbParser = new GerberParser();
		//gbParser.parse(new File("gerber/levistone_tcream.gtp"));
		GCodeParser gcParser = new GCodeParser();
		try {
			gcParser.parse(new File("gerber/levistone_tcream.ncd"));
		} catch (GCodeException e) {
			e.printStackTrace();
		}
	}

}

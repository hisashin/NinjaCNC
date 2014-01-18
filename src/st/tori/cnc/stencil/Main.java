package st.tori.cnc.stencil;

import java.io.File;

import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.GerberParser;

public class Main {

	public static void main(String[] args) {
		GerberParser gbParser = new GerberParser();
		Gerber gerber = gbParser.parse(new File("gerber/levistone_tcream.gtp"));
		/*
		try {
			GCodeParser gcParser = new GCodeParser();
			GCode codeNcd = gcParser.parse(new File("gerber/levistone_tcream.ncd"));
		} catch (GCodeException e) {
			e.printStackTrace();
		}
		*/
	}

}

package st.tori.cnc.stencil;

import java.io.File;

import st.tori.cnc.stencil.parser.GCodeParser;
import st.tori.cnc.stencil.parser.GerberParser;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Gerber!");
		GerberParser gbParser = new GerberParser();
		gbParser.parse(new File("gerber/levistone_tcream.gtp"));
		GCodeParser gcParser = new GCodeParser();
		gcParser.parse(new File("gerber/levistone_tcream.ncd"));
	}

}

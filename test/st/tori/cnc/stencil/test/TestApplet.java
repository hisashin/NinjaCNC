package st.tori.cnc.stencil.test;

import static org.junit.Assert.assertTrue;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.File;

import org.junit.Test;

import st.tori.cnc.stencil.canvas.applet.DimensionController;
import st.tori.cnc.stencil.gerber.exception.GerberException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.GerberParser;

public class TestApplet extends Applet {

	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GBL";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GBO";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GBP";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GBS";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GML";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GTL";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GTO";
	//private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GTP";
	private static final String GERBER_FILEPATH = "../sample/gerber/harpyx/HARPYX_140622.GTS";
	
	@Test
	public void paint(Graphics g) {
		try {
			GerberParser gbParser = new GerberParser();
			Gerber gerber = gbParser.parse(new File(GERBER_FILEPATH));
			DimensionController dc = new DimensionController(true,this,g,gerber.getXYMinMax());
			System.out.println(dc.toString());
			gerber.draw(dc);
			assertTrue(true);
		} catch (GerberException e) {
			e.printStackTrace();
		}
	}
	/*
	@Test
	public void paint(Graphics g) {
		try {
			GCodeParser gcParser = new GCodeParser();
			GCode code = gcParser.parse(Drill.ORIMIN_VC, new File("../sample/gcode/Levistone_tcream.ncd"));
			DimensionController dc = new DimensionController(true,this,g,code.getXYMinMax());
			System.out.println(dc.toString());
			code.draw(dc);
			assertTrue(true);
		} catch (GCodeException e) {
			e.printStackTrace();
		}
	}
	*/

}

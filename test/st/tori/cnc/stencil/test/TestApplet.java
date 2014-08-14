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

	@Test
	public void paintNcd(Graphics g) {
		try {
			GerberParser gbParser = new GerberParser();
			Gerber gerber = gbParser.parse(new File("../gerber/harpyx/HARPYX_140622.GTS"));
			DimensionController dc = new DimensionController(true,this,g,gerber.getXYMinMax());
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
			GCode code = gcParser.parse(Drill.ORIMIN_VC, new File("../gerber/Levistone_tcream.ncd"));
			DimensionController dc = new DimensionController(true,this,g,code.getXYMinMax());
			code.draw(dc);
			assertTrue(true);
		} catch (GCodeException e) {
			e.printStackTrace();
		}
	}
	*/

}

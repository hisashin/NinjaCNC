package st.tori.cnc.stencil.test;

import static org.junit.Assert.assertTrue;

import java.applet.Applet;
import java.awt.Graphics;
import java.io.File;

import org.junit.Test;

import st.tori.cnc.stencil.canvas.applet.DimensionController;
import st.tori.cnc.stencil.dxf.exception.DxfException;
import st.tori.cnc.stencil.dxf.parser.Dxf;
import st.tori.cnc.stencil.dxf.parser.DxfParser;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.exception.GCodeException;
import st.tori.cnc.stencil.util.FileUtil;

public class TestDxf extends Applet {

	//private static final String DXF_FILEPATH = "../sample/dxf/simple.dxf";
	//private static final String DXF_FILEPATH = "../sample/dxf/complexed.dxf";
	private static final String DXF_FILEPATH = "../sample/dxf/0.5mm.dxf";
	private static final String GCODE_FILEPATH = "../sample/dxf/0.5mm.ncd";
	
	@Test
	public void paint(Graphics g) {
		try {
			DxfParser dParser = new DxfParser();
			Dxf dxf = dParser.parse(Drill.ORIMIN_VC, new File(DXF_FILEPATH));
			DimensionController dc = new DimensionController(true,this,g,dxf.getXYMinMax());
			System.out.println(dc.toString());
			dxf.draw(dc);
			FileUtil.writeFileFromString(new File(GCODE_FILEPATH), dxf.toString());
			assertTrue(true);
		} catch (DxfException e) {
			e.printStackTrace();
		} catch (GCodeException e) {
			e.printStackTrace();
		}
	}

}

package st.tori.cnc.stencil.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import st.tori.cnc.stencil.canvas.shape.Square;
import st.tori.cnc.stencil.gcode.action.GCode;
import st.tori.cnc.stencil.gcode.drill.DrillOriminVC;
import st.tori.cnc.stencil.gcode.exception.GCodeException;
import st.tori.cnc.stencil.gcode.parser.GCodeParser;
import st.tori.cnc.stencil.util.FileUtil;

public class TestGCode {

	private static final String RET = "\n";
	private void assertGCode(String str, GCode code) {
		assertEquals(clean(str), clean(code.toString()));
	}
	private static String clean(String str) {
		if(str==null)return str;
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\\([^\\)]*\\)(\n)?", "");
		return str;
	}
	
	private static String TEST_HEADER;
	static{
		StringBuffer buf = new StringBuffer();
		buf.append("(Header)").append(RET);
		buf.append("G21G61G90").append(RET);
		buf.append("G00Z5.000").append(RET);
		buf.append("G00X0.000Y0.000").append(RET);
		buf.append("M03").append(RET);
		buf.append("G00Z0.500").append(RET);
		TEST_HEADER = buf.toString();
	}
	private static String TEST_FOOTER;
	static{
		StringBuffer buf = new StringBuffer();
		buf.append("(Footer)").append(RET);
		buf.append("G61").append(RET);
		buf.append("G00Z5.000").append(RET);
		//buf.append("G00X0.000Y00.000").append(RET);
		buf.append("G00X0.000Y0.000").append(RET);
		buf.append("M30").append(RET);
		TEST_FOOTER = buf.toString();
	}
	private static String TEST_SQUARE_CUT;
	static{
		StringBuffer buf = new StringBuffer();
		//buf.append("G00Z0.500").append(RET);
		buf.append("G00 X-0.100 Y-0.100").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X1.370 Y-0.100 F300.000").append(RET);
		buf.append("X1.370 Y1.370").append(RET);
		buf.append("X-0.100 Y1.370").append(RET);
		buf.append("X-0.100 Y-0.100").append(RET);
		buf.append("G00 Z0.500").append(RET);
		TEST_SQUARE_CUT = buf.toString();
	}

	@Test
	public void testSquareCut() {
		GCode code = new GCode();
		code.initialize(new DrillOriminVC(), 5.0, 0.5, -0.15, 100.0, 300.0);
		code.add(new Square(-0.1, -0.1, 1.37, 1.37));
		code.finalize();
		assertGCode(TEST_HEADER+TEST_SQUARE_CUT+TEST_FOOTER, code);
	}
	@Test
	public void testSimpleStencilWithSquareFromFile() {
		assertGCode(FileUtil.readFileAsString(new File("gerber/levistone_tcream.ncd")),
				new GCodeSampleLevistoneTCream());
	}
	@Test
	public void testParser() {
		try {
			File file = new File("gerber/levistone_tcream.ncd");
			GCodeParser gcParser = new GCodeParser();
			GCode codeNcd = gcParser.parse(file);
			assertGCode(FileUtil.readFileAsString(file), codeNcd);
		} catch (GCodeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

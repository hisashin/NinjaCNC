package st.tori.cnc.stencil.test;

import static org.junit.Assert.*;

import org.junit.Test;

import st.tori.cnc.stencil.gcode.action.GAction00;
import st.tori.cnc.stencil.gcode.action.GAction01;
import st.tori.cnc.stencil.gcode.action.GCode;

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
	@Test
	public void testHeader() {
		GCode code = new GCode();
		code.initialize(5.0, 0.5);
		assertGCode(TEST_HEADER, code);
	}
	private static String TEST_FOOTER;
	static{
		StringBuffer buf = new StringBuffer();
		buf.append("(Footer)").append(RET);
		buf.append("G61").append(RET);
		buf.append("G00Z5.000").append(RET);
		buf.append("G00X0.000Y00.000").append(RET);
		buf.append("M30").append(RET);
		TEST_FOOTER = buf.toString();
	}
	@Test
	public void testFooter() {
		GCode code = new GCode();
		code.initialize(5.0, 0.5);
		code.finalize();
		assertGCode(TEST_HEADER+TEST_FOOTER, code);
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
		code.initialize(5, 0.5);
		{
			GAction00 action = new GAction00(code);
			action.setX(-0.1);
			action.setY(-0.1);
			action.setZ(0.5);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setZ(-0.15);
			action.setF(100);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(1.37);
			action.setY(-0.1);
			action.setF(300);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(1.37);
			action.setY(1.37);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(-0.1);
			action.setY(1.37);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(-0.1);
			action.setY(-0.1);
			code.add(action);
		}
		{
			GAction00 action = new GAction00(code);
			action.setZ(0.5);
			code.add(action);
		}
		code.finalize();
		assertGCode(TEST_HEADER+TEST_SQUARE_CUT+TEST_FOOTER, code);
	}
	@Test
	public void testWholeStencil() {
		StringBuffer buf = new StringBuffer();
		buf.append("(Header)").append(RET);
		buf.append("G21G61G90").append(RET);
		buf.append("G00Z5.000").append(RET);
		buf.append("G00X0.000Y00.000").append(RET);
		buf.append("M03").append(RET);
		buf.append("G00Z0.500").append(RET);
		buf.append("G00 X-0.100 Y-0.100").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X1.370 Y-0.100 F300.000").append(RET);
		buf.append("X1.370 Y1.370").append(RET);
		buf.append("X-0.100 Y1.370").append(RET);
		buf.append("X-0.100 Y-0.100").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X2.735 Y-0.100").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X3.935 Y-0.100 F300.000").append(RET);
		buf.append("X3.935 Y1.370").append(RET);
		buf.append("X2.735 Y1.370").append(RET);
		buf.append("X2.735 Y-0.100").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X5.300 Y-0.100").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X6.770 Y-0.100 F300.000").append(RET);
		buf.append("X6.770 Y1.370").append(RET);
		buf.append("X5.300 Y1.370").append(RET);
		buf.append("X5.300 Y-0.100").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X8.735 Y3.185").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X9.436 Y3.185 F300.000").append(RET);
		buf.append("X9.436 Y4.186").append(RET);
		buf.append("X8.735 Y4.186").append(RET);
		buf.append("X8.735 Y3.185").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X8.735 Y4.687").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X9.436 Y4.687 F300.000").append(RET);
		buf.append("X9.436 Y5.687").append(RET);
		buf.append("X8.735 Y5.687").append(RET);
		buf.append("X8.735 Y4.687").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.237 Y4.687").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X10.937 Y4.687 F300.000").append(RET);
		buf.append("X10.937 Y5.687").append(RET);
		buf.append("X10.237 Y5.687").append(RET);
		buf.append("X10.237 Y4.687").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.237 Y3.185").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X10.937 Y3.185 F300.000").append(RET);
		buf.append("X10.937 Y4.186").append(RET);
		buf.append("X10.237 Y4.186").append(RET);
		buf.append("X10.237 Y3.185").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.237 Y7.186").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X10.937 Y7.186 F300.000").append(RET);
		buf.append("X10.937 Y8.186").append(RET);
		buf.append("X10.237 Y8.186").append(RET);
		buf.append("X10.237 Y7.186").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X8.735 Y7.186").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X9.436 Y7.186 F300.000").append(RET);
		buf.append("X9.436 Y8.186").append(RET);
		buf.append("X8.735 Y8.186").append(RET);
		buf.append("X8.735 Y7.186").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X7.237 Y7.186").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X7.937 Y7.186 F300.000").append(RET);
		buf.append("X7.937 Y8.186").append(RET);
		buf.append("X7.237 Y8.186").append(RET);
		buf.append("X7.237 Y7.186").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X5.736 Y7.186").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X6.436 Y7.186 F300.000").append(RET);
		buf.append("X6.436 Y8.186").append(RET);
		buf.append("X5.736 Y8.186").append(RET);
		buf.append("X5.736 Y7.186").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X5.296 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X5.997 Y8.697 F300.000").append(RET);
		buf.append("X5.997 Y10.398").append(RET);
		buf.append("X5.296 Y10.398").append(RET);
		buf.append("X5.296 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X6.294 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X6.995 Y8.697 F300.000").append(RET);
		buf.append("X6.995 Y10.398").append(RET);
		buf.append("X6.294 Y10.398").append(RET);
		buf.append("X6.294 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X7.295 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X7.996 Y8.697 F300.000").append(RET);
		buf.append("X7.996 Y10.398").append(RET);
		buf.append("X7.295 Y10.398").append(RET);
		buf.append("X7.295 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X8.296 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X8.996 Y8.697 F300.000").append(RET);
		buf.append("X8.996 Y10.398").append(RET);
		buf.append("X8.296 Y10.398").append(RET);
		buf.append("X8.296 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X9.297 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X9.997 Y8.697 F300.000").append(RET);
		buf.append("X9.997 Y10.398").append(RET);
		buf.append("X9.297 Y10.398").append(RET);
		buf.append("X9.297 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y9.871").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y9.871 F300.000").append(RET);
		buf.append("X12.296 Y10.571").append(RET);
		buf.append("X10.595 Y10.571").append(RET);
		buf.append("X10.595 Y9.871").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y10.872").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y10.872 F300.000").append(RET);
		buf.append("X12.296 Y11.572").append(RET);
		buf.append("X10.595 Y11.572").append(RET);
		buf.append("X10.595 Y10.872").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y11.872").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y11.872 F300.000").append(RET);
		buf.append("X12.296 Y12.573").append(RET);
		buf.append("X10.595 Y12.573").append(RET);
		buf.append("X10.595 Y11.872").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y12.873").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y12.873 F300.000").append(RET);
		buf.append("X12.296 Y13.573").append(RET);
		buf.append("X10.595 Y13.573").append(RET);
		buf.append("X10.595 Y12.873").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y13.871").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y13.871 F300.000").append(RET);
		buf.append("X12.296 Y14.572").append(RET);
		buf.append("X10.595 Y14.572").append(RET);
		buf.append("X10.595 Y13.871").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X10.595 Y14.872").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X12.296 Y14.872 F300.000").append(RET);
		buf.append("X12.296 Y15.572").append(RET);
		buf.append("X10.595 Y15.572").append(RET);
		buf.append("X10.595 Y14.872").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X9.297 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X9.997 Y15.047 F300.000").append(RET);
		buf.append("X9.997 Y16.748").append(RET);
		buf.append("X9.297 Y16.748").append(RET);
		buf.append("X9.297 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X8.296 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X8.996 Y15.047 F300.000").append(RET);
		buf.append("X8.996 Y16.748").append(RET);
		buf.append("X8.296 Y16.748").append(RET);
		buf.append("X8.296 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X7.295 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X7.996 Y15.047 F300.000").append(RET);
		buf.append("X7.996 Y16.748").append(RET);
		buf.append("X7.295 Y16.748").append(RET);
		buf.append("X7.295 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X6.294 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X6.995 Y15.047 F300.000").append(RET);
		buf.append("X6.995 Y16.748").append(RET);
		buf.append("X6.294 Y16.748").append(RET);
		buf.append("X6.294 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X5.296 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X5.997 Y15.047 F300.000").append(RET);
		buf.append("X5.997 Y16.748").append(RET);
		buf.append("X5.296 Y16.748").append(RET);
		buf.append("X5.296 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X4.295 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X4.996 Y15.047 F300.000").append(RET);
		buf.append("X4.996 Y16.748").append(RET);
		buf.append("X4.295 Y16.748").append(RET);
		buf.append("X4.295 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X3.295 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X3.995 Y15.047 F300.000").append(RET);
		buf.append("X3.995 Y16.748").append(RET);
		buf.append("X3.295 Y16.748").append(RET);
		buf.append("X3.295 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X2.296 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X2.997 Y15.047 F300.000").append(RET);
		buf.append("X2.997 Y16.748").append(RET);
		buf.append("X2.296 Y16.748").append(RET);
		buf.append("X2.296 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X1.296 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X1.996 Y15.047 F300.000").append(RET);
		buf.append("X1.996 Y16.748").append(RET);
		buf.append("X1.296 Y16.748").append(RET);
		buf.append("X1.296 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X0.295 Y15.047").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X0.995 Y15.047 F300.000").append(RET);
		buf.append("X0.995 Y16.748").append(RET);
		buf.append("X0.295 Y16.748").append(RET);
		buf.append("X0.295 Y15.047").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X0.295 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X0.995 Y8.697 F300.000").append(RET);
		buf.append("X0.995 Y10.398").append(RET);
		buf.append("X0.295 Y10.398").append(RET);
		buf.append("X0.295 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X1.296 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X1.996 Y8.697 F300.000").append(RET);
		buf.append("X1.996 Y10.398").append(RET);
		buf.append("X1.296 Y10.398").append(RET);
		buf.append("X1.296 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X2.296 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X2.997 Y8.697 F300.000").append(RET);
		buf.append("X2.997 Y10.398").append(RET);
		buf.append("X2.296 Y10.398").append(RET);
		buf.append("X2.296 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X3.295 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X3.995 Y8.697 F300.000").append(RET);
		buf.append("X3.995 Y10.398").append(RET);
		buf.append("X3.295 Y10.398").append(RET);
		buf.append("X3.295 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X4.295 Y8.697").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X4.996 Y8.697 F300.000").append(RET);
		buf.append("X4.996 Y10.398").append(RET);
		buf.append("X4.295 Y10.398").append(RET);
		buf.append("X4.295 Y8.697").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X3.856 Y6.874").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X4.556 Y6.874 F300.000").append(RET);
		buf.append("X4.556 Y7.874").append(RET);
		buf.append("X3.856 Y7.874").append(RET);
		buf.append("X3.856 Y6.874").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X2.355 Y6.874").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X3.055 Y6.874 F300.000").append(RET);
		buf.append("X3.055 Y7.874").append(RET);
		buf.append("X2.355 Y7.874").append(RET);
		buf.append("X2.355 Y6.874").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X2.735 Y5.300").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X3.935 Y5.300 F300.000").append(RET);
		buf.append("X3.935 Y6.770").append(RET);
		buf.append("X2.735 Y6.770").append(RET);
		buf.append("X2.735 Y5.300").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X5.300 Y5.300").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X6.770 Y5.300 F300.000").append(RET);
		buf.append("X6.770 Y6.770").append(RET);
		buf.append("X5.300 Y6.770").append(RET);
		buf.append("X5.300 Y5.300").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("G00 X-0.100 Y5.300").append(RET);
		buf.append("G01 Z-0.150 F100.000").append(RET);
		buf.append("G01 X1.370 Y5.300 F300.000").append(RET);
		buf.append("X1.370 Y6.770").append(RET);
		buf.append("X-0.100 Y6.770").append(RET);
		buf.append("X-0.100 Y5.300").append(RET);
		buf.append("G00 Z0.500").append(RET);
		buf.append("(Footer)").append(RET);
		buf.append("G61").append(RET);
		buf.append("G00Z5.000").append(RET);
		buf.append("G00X0.000Y00.000").append(RET);
		buf.append("M30").append(RET);		
		GCode code = new GCode();
		{
			GAction00 action = new GAction00(code);
			action.setX(-0.1);
			action.setY(-0.1);
			action.setZ(0.5);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setZ(-0.15);
			action.setF(100);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(1.37);
			action.setY(-0.1);
			action.setF(300);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(1.37);
			action.setY(1.37);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(-0.1);
			action.setY(1.37);
			code.add(action);
		}
		{
			GAction01 action = new GAction01(code);
			action.setX(-0.1);
			action.setY(-0.1);
			code.add(action);
		}
		{
			GAction00 action = new GAction00(code);
			action.setZ(0.5);
			code.add(action);
		}
		assertGCode(buf.toString(), code);
	}

}

package st.tori.cnc.stencil.test;

import static org.junit.Assert.*;

import org.junit.Test;

import st.tori.cnc.stencil.gcode.action.GAction;
import st.tori.cnc.stencil.gcode.action.GAction00;
import st.tori.cnc.stencil.gcode.action.GAction21;
import st.tori.cnc.stencil.gcode.action.GAction61;
import st.tori.cnc.stencil.gcode.action.GAction90;
import st.tori.cnc.stencil.gcode.action.GCode;

public class TestGCode {

	private static final String RET = "\n";
	
	@Test
	public void testHeader() {
		StringBuffer buf = new StringBuffer("G21G61G90").append(RET);
		buf.append("G00Z5.000").append(RET);
		buf.append("G00X0.000Y00.000").append(RET);
		buf.append("M03").append(RET);
		buf.append("G00Z0.500").append(RET);

		GCode code = new GCode();
		code.add(new GAction21());
		code.add(new GAction61());
		code.add(new GAction90());
		GAction00 action = new GAction00();
		action.
		code.add(new GAction00());

		assertEquals(buf.toString(), code.toString());
	}

}

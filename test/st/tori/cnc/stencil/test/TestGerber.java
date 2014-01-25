package st.tori.cnc.stencil.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.util.FileUtil;

public class TestGerber {

	private static final String RET = "\n";
	private void assertGerber(String str, Gerber gerber) {
		assertEquals(clean(str), clean(gerber.toString()));
	}
	private static String clean(String str) {
		if(str==null)return str;
		str = str.replaceAll(" ", "");
		//str = str.replaceAll("\\([^\\)]*\\)(\n)?", "");
		return str;
	}
	
	@Test
	public void testSimpleStencilWithSquareFromFile() {
		assertGerber(FileUtil.readFileAsString(new File("gerber/levistone_tcream.gtp")),
				new GerberSampleLevistoneTCream());
	}

}

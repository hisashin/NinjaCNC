package st.tori.cnc.stencil.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import st.tori.cnc.stencil.gerber.exception.GerberException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.GerberParser;
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
	/*
	@Test
	public void testSimpleStencilWithSquareFromFile() {
		assertGerber(FileUtil.readFileAsString(new File("gerber/Levistone_tcream.gtp")),
				new GerberSampleLevistoneTCream());
	}
	*/
	@Test
	public void testParser() {
		try {
			File file = new File("gerber/Test.gbr");
			GerberParser gbParser = new GerberParser();
			Gerber codeGerber = gbParser.parse(file);
			assertGerber(FileUtil.readFileAsString(file), codeGerber);
		} catch (GerberException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

package st.tori.cnc.stencil.gerber.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import st.tori.cnc.stencil.util.FileUtil;


public class GerberParser {

	/*
	 * Read gerber into something
	 */
	public Gerber parse(File file) {
		Gerber gerber = new Gerber();
		List<String> list = FileUtil.readFileAsStringList(file);
		Iterator<String> ite = list.iterator();
		String line;
		while(ite.hasNext()) {
			line = ite.next();
			System.out.println(line);
		}
		return gerber;
	}

}

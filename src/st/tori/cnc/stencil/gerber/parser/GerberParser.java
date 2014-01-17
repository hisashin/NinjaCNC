package st.tori.cnc.stencil.gerber.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class GerberParser {

	/*
	 * Read gerber into something
	 */
	public void parse(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int lineCount = 0;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				lineCount++;
			}
			br.close();
			System.out.println("There are "+lineCount+" lines");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}

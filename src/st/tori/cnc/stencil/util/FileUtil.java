package st.tori.cnc.stencil.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtil {

	private static final String RET = "\n";

	public static String readFileAsString(File file) {
		StringBuffer buf = new StringBuffer();
		List<String> list = readFileAsStringList(file);
		Iterator<String> ite = list.iterator();
		while (ite.hasNext())
			buf.append(ite.next()).append(RET);
		return buf.toString();
	}

	public static List<String> readFileAsStringList(File file) {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null)
				list.add(line);
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return list;
	}

	public static void writeFileFromString(File file, String str) {
		if(file==null||str==null)return;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

package st.tori.cnc.stencil.gerber.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gerber.exception.GerberException;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedPrefixException;
import st.tori.cnc.stencil.gerber.statement.Comment;
import st.tori.cnc.stencil.gerber.statement.StatementFactory;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.util.FileUtil;


public class GerberParser {

	private static final Pattern PATTERN_COMMENT	 = Pattern.compile("^G04([^\\*]+)\\*$");
	private static final Pattern PATTERN_SINGLE_G	 = Pattern.compile("^G([0-9]{2})(.*)\\*$");
	private static final Pattern PATTERN_SINGLE_D	 = Pattern.compile("^D([0-9]{2})(.*)\\*$");
	private static final Pattern PATTERN_SINGLE_P	 = Pattern.compile("^%(..)(.*)$");

	public Gerber parse(File file) throws GerberException {
		Gerber gerber = new Gerber();
		StatementInterface statement = null;
		String line;
		List<String> list = FileUtil.readFileAsStringList(file);
		Iterator<String> ite = list.iterator();
		int lineIndex = 0;
		while(ite.hasNext()) {
			line = ite.next();
			lineIndex++;
			System.out.println(line);
			line = line.trim();
			Matcher matcher;
			matcher = PATTERN_COMMENT.matcher(line);
			if(matcher.find()) {
				gerber.add(new Comment(matcher.group(1)));
				continue;
			}
			if("M02*".equals(line)) {
				gerber.finalize();
				continue;
			}
			matcher = PATTERN_SINGLE_G.matcher(line);
			if(matcher.find()) {
				System.out.println("PatternSingleG matched: G"+matcher.group(1)+":"+matcher.group(2));
				int gIndex = Integer.parseInt(matcher.group(1));
				String param = matcher.group(2);
				if(statement!=null){
					gerber.add(statement);
				}
				statement = StatementFactory.createGStatement(gIndex,gerber);
				continue;
			}
			matcher = PATTERN_SINGLE_D.matcher(line);
			if(matcher.find()) {
				System.out.println("PatternSingleD matched: D"+matcher.group(1)+":"+matcher.group(2));
				int dIndex = Integer.parseInt(matcher.group(1));
				String param = matcher.group(2);
				if(statement!=null){
					gerber.add(statement);
				}
				statement = StatementFactory.createDStatement(dIndex,gerber);
				continue;
			}
			matcher = PATTERN_SINGLE_P.matcher(line);
			if(matcher.find()) {
				String parameterCode = matcher.group(1);
				String modifiers = matcher.group(2);
				while(!modifiers.endsWith("%") && ite.hasNext()) {
					modifiers += ite.next().trim();
					lineIndex++;
				}
				if(!modifiers.endsWith("%"))
					throw new IllegalParameterException(lineIndex, parameterCode);
				else if("%".equals(modifiers))
					modifiers = null;
				else
					modifiers = modifiers.substring(0,modifiers.length()-1);
				System.out.println("PatternSingleP matched: "+parameterCode+":"+modifiers);
				if(statement!=null){
					gerber.add(statement);
				}
				statement = StatementFactory.createPStatement(parameterCode, modifiers, gerber);
				continue;
			}
			throw new UnsupportedPrefixException(line);
		}
		if(statement!=null)gerber.add(statement);
		statement = null;
		
		System.out.println("There are "+list.size()+" lines,"+gerber.size()+" statements");
		return gerber;
	}

}

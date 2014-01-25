package st.tori.cnc.stencil.gerber.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.InvalidIndexException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.statement.Comment;
import st.tori.cnc.stencil.gerber.statement.GStatement;
import st.tori.cnc.stencil.gerber.statement.MStatement02;
import st.tori.cnc.stencil.gerber.statement.StatementFactory;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.util.FileUtil;


public class GerberParser {

	private static final Pattern PATTERN_COMMENT	 = Pattern.compile("^G04([^\\*]+)\\*$");
	private static final Pattern PATTERN_SINGLE_G	 = Pattern.compile("^G([0-9]{2})(.*)\\*$");
	private static final Pattern PATTERN_SINGLE_D	 = Pattern.compile("^D([0-9]{2})(.*)\\*$");
	private static final Pattern PATTERN_SINGLE_ELSE = Pattern.compile("^%(.*)%");

	public Gerber parse(File file) throws InvalidIndexException, NoLastStatementExistsException, IllegalReflectionException {
		Gerber gerber = new Gerber();
		GStatement statement = null;
		String line;
		List<String> list = FileUtil.readFileAsStringList(file);
		Iterator<String> ite = list.iterator();
		while(ite.hasNext()) {
			line = ite.next();
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
				continue;
			}
			matcher = PATTERN_SINGLE_ELSE.matcher(line);
			if(matcher.find()) {
				System.out.println("PatternSingleElse matched: "+matcher.group(1));
				continue;
			}
			System.out.println("NOMATCH");
			continue;
			/*
			while(matcher.find()) {
				String prefix = matcher.group(1);
				String valStr = matcher.group(2);
				double val = Double.valueOf(valStr);
				if(valStr.indexOf(".")<0&&!"G".equals(prefix)&&!"M".equals(prefix))val /= 1000.0;
				if("G".equals(prefix)) {
					if(action!=null){
						gCode.add(action);
					}
					action = ActionFactory.createGAction((int)val,gCode);
				}else if("M".equals(prefix)) {
					gCode.add(ActionFactory.createMAction((int)val));
				}else if("X".equals(prefix)) {
					if(action==null)action = ActionFactory.createGAction(-1,gCode);
					if(action instanceof PositionXYZInterface)
						((PositionXYZInterface)action).setX(val);
					else
						throw new PositionNotSupportedException(line);
				}else if("Y".equals(prefix)) {
					if(action==null)action = ActionFactory.createGAction(-1,gCode);
					if(action instanceof PositionXYZInterface)
						((PositionXYZInterface)action).setY(val);
					else
						throw new PositionNotSupportedException(line);
				}else if("Z".equals(prefix)) {
					if(action==null)action = ActionFactory.createGAction(-1,gCode);
					if(action instanceof PositionXYZInterface)
						((PositionXYZInterface)action).setZ(val);
					else
						throw new PositionNotSupportedException(line);
				}else if("F".equals(prefix)) {
					if(action==null)action = ActionFactory.createGAction(-1,gCode);
					if(action instanceof SpeedInterface)
						((SpeedInterface)action).setF(val);
					else
						throw new SpeedNotSupportedException(line);
				}else{
					throw new UnsupportedPrefixException(prefix);
				}
			}
			if(action!=null)gCode.add(action);
			*/
			//statement = null;
		}
		System.out.println("There are "+list.size()+" lines,"+gerber.size()+" statements");
		return gerber;
	}

}

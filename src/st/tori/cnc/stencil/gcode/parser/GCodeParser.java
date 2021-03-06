package st.tori.cnc.stencil.gcode.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.gcode.action.ActionFactory;
import st.tori.cnc.stencil.gcode.action.Comment;
import st.tori.cnc.stencil.gcode.action.GAction;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.UnsupportedIndexException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.PositionNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.SpeedNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.UnsupportedPrefixException;
import st.tori.cnc.stencil.util.FileUtil;

public class GCodeParser {

	private static final Pattern PATTERN_COMMENT = Pattern.compile("\\(([^\\)]+)\\)");
	private static final Pattern PATTERN = Pattern.compile("([A-Z])([\\.\\-0-9]+)");

	public GCode parse(Drill drill, File file) throws UnsupportedPrefixException, UnsupportedIndexException, PositionNotSupportedException, SpeedNotSupportedException, NoLastActionExistsException, IllegalReflectionException {
		GCode gCode = new GCode(drill);
		GAction action = null;
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
				gCode.add(new Comment(matcher.group(1)));
				continue;
			}
			matcher = PATTERN.matcher(line);
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
			action = null;
		}
		System.out.println("There are "+list.size()+" lines,"+gCode.size()+" actions");
		return gCode;
	}

}

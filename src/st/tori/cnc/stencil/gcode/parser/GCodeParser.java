package st.tori.cnc.stencil.gcode.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gcode.action.ActionFactory;
import st.tori.cnc.stencil.gcode.action.ActionInterface;
import st.tori.cnc.stencil.gcode.action.GAction;
import st.tori.cnc.stencil.gcode.action.GCode;
import st.tori.cnc.stencil.gcode.action.PositionXYZInterface;
import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.InvalidIndexException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.PositionNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.SpeedNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.UnsupportedPrefixException;
import st.tori.cnc.stencil.util.FileUtil;

public class GCodeParser {

	private static final Pattern PATTERN = Pattern.compile("([A-Z])([\\.\\-0-9]+)");

	public GCode parse(File file) throws UnsupportedPrefixException, InvalidIndexException, PositionNotSupportedException, SpeedNotSupportedException, NoLastActionExistsException, IllegalReflectionException {
		GCode gCode = new GCode();
		GAction action = null;
		String line;
		List<String> list = FileUtil.readFileAsStringList(file);
		Iterator<String> ite = list.iterator();
		while(ite.hasNext()) {
			line = ite.next();
			System.out.println(line);
			line = line.trim();
			if(line.startsWith("("))continue;
			Matcher matcher;
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

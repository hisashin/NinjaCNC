package st.tori.cnc.stencil.gerber.statement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Step and Repeat
 */
public class PStatementSR extends PStatement implements UnsupportedStatementInterface {

	@Override
	protected String getParameterCode() {	return "SR";	}

	public PStatementSR(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers, gerber);
		if(modifiers!=null) {
			Matcher matcher = PATTERN_MODIFIER.matcher(modifiers);
			if(matcher.find()) {
				xRepeats = parseInt(matcher.group(2), 1);
				yRepeats = parseInt(matcher.group(4), 1);
				xStep = parseDouble(matcher.group(6), 0);
				yStep = parseDouble(matcher.group(8), 0);
				//System.out.println("xRepeat="+xRepeat+",yRepeat="+yRepeat+",xStep="+xStep+",yStep="+yStep);
				return;
			}
		}
		throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}
	private static final Pattern PATTERN_MODIFIER = Pattern.compile("(X([0-9]+))?(Y([0-9]+))?(I([0-9\\.]+))?(J([0-9\\.]+))?");
	private static int parseInt(String val, int defaultValue) {
		return (val==null)?defaultValue:Integer.parseInt(val);
	}
	private static double parseDouble(String val, double defaultValue) {
		return (val==null)?defaultValue:Double.parseDouble(val);
	}
	
	public int xRepeats;
	public int yRepeats;
	public double xStep;
	public double yStep;

}

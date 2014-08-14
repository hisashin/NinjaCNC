package st.tori.cnc.stencil.gerber.statement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.parser.Gerber.COORDINATE_VALUES_NOTATION;
import st.tori.cnc.stencil.gerber.parser.Gerber.ZERO_OMISSION_MODE;

/*
 * Format Specification
 */
public class PStatementFS extends PStatement {

	@Override
	protected String getParameterCode() {	return "FS";	}

	private static final Pattern PATTERN_MODIFIERS = Pattern.compile("([LT])([AI])X([0-9])([0-9])Y([0-9])([0-9])");
	public PStatementFS(String modifiers, Gerber gerber) throws IllegalParameterModifiersException {
		super(modifiers,gerber);
		Matcher matcher = PATTERN_MODIFIERS.matcher(modifiers);
		if(!matcher.find())
			throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
		String LT = matcher.group(1);
		if("L".equals(LT))
			zeroOmissionMode = ZERO_OMISSION_MODE.OMIT_LEADING_ZEROS;
		else if("L".equals(LT))
			zeroOmissionMode = ZERO_OMISSION_MODE.OMIT_LEADING_ZEROS;
		String AI = matcher.group(2);
		if("A".equals(AI))
			coordinateValuesNotation = COORDINATE_VALUES_NOTATION.ABSOLUTE_NOTATION;
		else if("I".equals(AI))
			coordinateValuesNotation = COORDINATE_VALUES_NOTATION.INCREMENTAL_NOTATION;
		XN = parseXY(matcher.group(3));
		XM = parseXY(matcher.group(4));
		YN = parseXY(matcher.group(5));
		YM = parseXY(matcher.group(6));
	}
	private int parseXY(String value) throws IllegalParameterModifiersException {
		int intVal = Integer.parseInt(value);
		if(intVal<0||intVal>8)
			throw new IllegalParameterModifiersException("Modifiers '"+getModifiers()+"' XY is out of bounds 0<=N<=7");
		return intVal;
	}
	
	public ZERO_OMISSION_MODE zeroOmissionMode;
	public COORDINATE_VALUES_NOTATION coordinateValuesNotation;
	public int XN;
	public int XM;
	public int YN;
	public int YM;
}

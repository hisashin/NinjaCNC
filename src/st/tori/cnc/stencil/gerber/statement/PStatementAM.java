package st.tori.cnc.stencil.gerber.statement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.gerber.exception.ArithmeticExpressionUnsupportedException;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Aperture Macro
 */
public class PStatementAM extends PStatement {

	@Override
	protected String getParameterCode() {	return "AM";	}

	private static final Pattern PATTERN_AM_PARAMETER = Pattern.compile("^([^\\*]+)\\*(.*)$");
	public PStatementAM(String modifiers, Gerber gerber) throws IllegalParameterModifiersException, ArithmeticExpressionUnsupportedException {
		super(modifiers, gerber);
		System.out.println("modifiers="+modifiers);
		Matcher matcher;
		matcher = PATTERN_AM_PARAMETER.matcher(modifiers);
		if(matcher.find()) {
			String name = matcher.group(1);
			String content = matcher.group(2);
			System.out.println("name="+name+",content="+content);
			String[] array = (content==null)?new String[0]:content.split("\\*");
			for(String str:array) {
				System.out.println("str="+str);
				if(isComment(str))
					continue;
				if(isVariableDefinition(str))
					throw new ArithmeticExpressionUnsupportedException(str);
			}
		}
		//throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
	}
	
	private static final Pattern PATTERN_COMMENT = Pattern.compile("^0 ([^|]+?)$");
	private boolean isComment(String str) {
		return PATTERN_COMMENT.matcher(str).find();
	}

	private static final Pattern PATTERN_VARIABLE_DEFINITON = Pattern.compile("^\\$([0-9]+)=(.*)$");
	private boolean isVariableDefinition(String str) {
		return PATTERN_VARIABLE_DEFINITON.matcher(str).find();
	}
	private static final Pattern PATTERN_ARITHMETIC_EXPRESSION = Pattern.compile("\\$([0-9]+)");
	private boolean hasArithmeticExpression(String str) {
		return PATTERN_ARITHMETIC_EXPRESSION.matcher(str).find();
	}

}

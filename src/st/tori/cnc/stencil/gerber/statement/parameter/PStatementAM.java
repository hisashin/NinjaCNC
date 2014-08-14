package st.tori.cnc.stencil.gerber.statement.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.source.tree.ModifiersTree;

import st.tori.cnc.stencil.gerber.exception.ArithmeticExpressionUnsupportedException;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedMacroException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.StatementFactory;
import st.tori.cnc.stencil.gerber.statement.StatementFactory.ModifiersContainer;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacro;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroCenterLine;

/*
 * Aperture Macro
 */
public class PStatementAM extends PStatement {

	private String name;
	private List<GerberMacro> macros = new ArrayList<GerberMacro>();

	@Override
	protected String getParameterCode() {	return "AM";	}

	private static final Pattern PATTERN_AM_PARAMETER = Pattern.compile("^([^\\*]+)\\*(.*)$");
	private static final Pattern PATTERN_PRIMITIVE = Pattern.compile("^([0-9]+),(.*)$");
	public PStatementAM(String modifiers, Gerber gerber) throws IllegalParameterModifiersException, ArithmeticExpressionUnsupportedException, UnsupportedMacroException {
		super(modifiers, gerber);
		System.out.println("modifiers="+modifiers);
		Matcher matcher;
		matcher = PATTERN_AM_PARAMETER.matcher(modifiers);
		if(!matcher.find())
			throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
		this.name = matcher.group(1);
		String content = matcher.group(2);
		System.out.println("name="+name+",content="+content);
		String[] array = (content==null)?new String[0]:content.split("\\*");
		int primitiveCode;
		String modifiersStr;
		for(String str:array) {
			System.out.println("str="+str);
			if(isComment(str))
				continue;
			if(isVariableDefinition(str))
				throw new ArithmeticExpressionUnsupportedException(str);
			matcher = PATTERN_PRIMITIVE.matcher(str);
			if(!matcher.find())
				throw new IllegalParameterModifiersException("Modifiers '"+modifiers+"' is illegal for "+getSimpleName());
			primitiveCode = Integer.parseInt(matcher.group(1));
			modifiersStr = matcher.group(2);
			GerberMacro macro = StatementFactory.createMacro(primitiveCode, modifiersStr);
			if(macro==null)continue;
			macros.add(macro);
		}
	}
	
	public String getName(){	return name;	}
	public List<GerberMacro> getMacros(){	return macros;	}
	
	private static final Pattern PATTERN_COMMENT = Pattern.compile("^0 ([^|]+?)$");
	private boolean isComment(String str) {
		return PATTERN_COMMENT.matcher(str).find();
	}

	private static final Pattern PATTERN_VARIABLE_DEFINITON = Pattern.compile("^\\$(.+?)=(.*)$");
	private boolean isVariableDefinition(String str) {
		return PATTERN_VARIABLE_DEFINITON.matcher(str).find();
	}
	private static final Pattern PATTERN_ARITHMETIC_EXPRESSION = Pattern.compile("\\$([0-9]+)");
	private boolean hasArithmeticExpression(String str) {
		return PATTERN_ARITHMETIC_EXPRESSION.matcher(str).find();
	}

}

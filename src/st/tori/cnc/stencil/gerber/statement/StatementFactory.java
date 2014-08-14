package st.tori.cnc.stencil.gerber.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.exception.ApertureNotDefinedException;
import st.tori.cnc.stencil.gerber.exception.ArithmeticExpressionUnsupportedException;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.exception.IllegalPositionException;
import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedApertureException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedIndexException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedMacroException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedParameterCodeException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberAperture;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberApertureCircle;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberApertureMacro;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberApertureObround;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberApertureRectangle;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberApertureRegularPolygon;
import st.tori.cnc.stencil.gerber.statement.aperture.UnsupportedApertureInterface;
import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifierHole;
import st.tori.cnc.stencil.gerber.statement.aperture.modifier.ApertureModifierRectanglar;
import st.tori.cnc.stencil.gerber.statement.function.DStatement;
import st.tori.cnc.stencil.gerber.statement.function.DStatement01;
import st.tori.cnc.stencil.gerber.statement.function.DStatement02;
import st.tori.cnc.stencil.gerber.statement.function.DStatement03;
import st.tori.cnc.stencil.gerber.statement.function.DStatement10orHigher;
import st.tori.cnc.stencil.gerber.statement.function.GStatement;
import st.tori.cnc.stencil.gerber.statement.function.GStatement01;
import st.tori.cnc.stencil.gerber.statement.function.GStatement02;
import st.tori.cnc.stencil.gerber.statement.function.GStatement03;
import st.tori.cnc.stencil.gerber.statement.function.GStatement04;
import st.tori.cnc.stencil.gerber.statement.function.GStatement36;
import st.tori.cnc.stencil.gerber.statement.function.GStatement37;
import st.tori.cnc.stencil.gerber.statement.function.GStatement74;
import st.tori.cnc.stencil.gerber.statement.function.GStatement75;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacro;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroCenterLine;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroCircle;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroLowerLeftLine;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroMoire;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroOutline;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroOutline.ArithmeticExpressionXY;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroPolygon;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroThermal;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacroVectorLine;
import st.tori.cnc.stencil.gerber.statement.macro.UnsupportedMacroInterface;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatement;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementAD;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementAM;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementFS;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementIP;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementLP;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementMO;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementOF;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementSR;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementTA;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementTD;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementTF;

public class StatementFactory {

	public static GStatement createGStatement(int gIndex, Gerber gerber) throws UnsupportedIndexException, NoLastStatementExistsException, IllegalReflectionException {
		GStatement statement = null;
		if(gIndex<0)
			statement = gerber.cloneLastStatement();
		else if(gIndex==1)
			statement = new GStatement01(gerber);
		else if(gIndex==2)
			statement = new GStatement02(gerber);
		else if(gIndex==3)
			statement = new GStatement03(gerber);
		else if(gIndex==4)
			statement = new GStatement04(gerber);
		else if(gIndex==36)
			statement = new GStatement36(gerber);
		else if(gIndex==37)
			statement = new GStatement37(gerber);
		else if(gIndex==74)
			statement = new GStatement74(gerber);
		else if(gIndex==75)
			statement = new GStatement75(gerber);
		if(statement==null || statement instanceof UnsupportedStatementInterface)
			throw new UnsupportedIndexException("G",gIndex);
		return statement;
	}
	
	public static DStatement createDStatement(int dIndex, String positionStr, Gerber gerber) throws UnsupportedIndexException, ApertureNotDefinedException, IllegalPositionException {
		DStatement statement = null;
		PositionXYInterface position = parsePosition(positionStr);
		if(dIndex==1)
			statement = new DStatement01(position, gerber);
		else if(dIndex==2)
			statement = new DStatement02(position, gerber);
		else if(dIndex==3)
			statement = new DStatement03(position, gerber);
		else if(dIndex>=10)
			statement = new DStatement10orHigher(dIndex, gerber);
		if(statement==null || statement instanceof UnsupportedStatementInterface)
			throw new UnsupportedIndexException("D",dIndex);
		return statement;
	}

	public static PStatement createPStatement(String parameterCode, String modifiers, Gerber gerber) throws UnsupportedParameterCodeException, IllegalParameterModifiersException, ArithmeticExpressionUnsupportedException, UnsupportedMacroException {
		PStatement statement = null;
		if("FS".equals(parameterCode))
			statement = new PStatementFS(modifiers, gerber);
		else if("MO".equals(parameterCode))
			statement = new PStatementMO(modifiers, gerber);
		else if("IP".equals(parameterCode))
			statement = new PStatementIP(modifiers, gerber);
		else if("AD".equals(parameterCode))
			statement = new PStatementAD(modifiers, gerber);
		else if("AM".equals(parameterCode))
			statement = new PStatementAM(modifiers, gerber);
		else if("SR".equals(parameterCode))
			statement = new PStatementSR(modifiers, gerber);
		else if("LP".equals(parameterCode))
			statement = new PStatementLP(modifiers, gerber);
		else if("TF".equals(parameterCode))
			statement = new PStatementTF(modifiers, gerber);
		else if("TD".equals(parameterCode))
			statement = new PStatementTD(modifiers, gerber);
		else if("TA".equals(parameterCode))
			statement = new PStatementTA(modifiers, gerber);
		else if("OF".equals(parameterCode))
			statement = new PStatementOF(modifiers, gerber);
		if(statement==null || statement instanceof UnsupportedStatementInterface)
			throw new UnsupportedParameterCodeException(parameterCode,modifiers);
		return statement;
	}

	public static GerberAperture createAperture(int dcode, String type, String modifiersStr, Gerber gerber) throws UnsupportedApertureException, ApertureMacroNotDefinedException {
		GerberAperture aperture = null;
		ModifiersContainer container = new ModifiersContainer("X", modifiersStr);
		if("C".equals(type))
			aperture = new GerberApertureCircle(dcode, container.getAsDouble(0), container.getApertureModifier(1), gerber);
		else if("R".equals(type))
			aperture = new GerberApertureRectangle(dcode, container.getAsDouble(0), container.getAsDouble(1), container.getApertureModifier(2), gerber);
		else if("O".equals(type))
			aperture = new GerberApertureObround(dcode, container.getAsDouble(0), container.getAsDouble(1), container.getApertureModifier(2), gerber);
		else if("P".equals(type))
			aperture = new GerberApertureRegularPolygon(dcode, container.getAsDouble(0), container.getAsInt(1), container.getAsDouble(2), container.getApertureModifier(3), gerber);
		else
			aperture = new GerberApertureMacro(dcode, type, gerber);
		if(aperture==null || aperture instanceof UnsupportedApertureInterface)
			throw new UnsupportedApertureException(dcode, type, modifiersStr);
		return aperture;
	}
	
	public static GerberMacro createMacro(int primitiveCode, String modifiersStr) throws UnsupportedMacroException {
		GerberMacro macro = null;
		ModifiersContainer container = new ModifiersContainer(",",modifiersStr);
		if(primitiveCode==1)
			macro = new GerberMacroCircle(container.getAsInt(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3));
		else if(primitiveCode==2||primitiveCode==20)
			macro = new GerberMacroVectorLine(container.getAsInt(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionDouble(5), container.getAsArithmeticExpressionDouble(6));
		else if(primitiveCode==21)
			macro = new GerberMacroCenterLine(container.getAsInt(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionDouble(5));
		else if(primitiveCode==22)
			macro = new GerberMacroLowerLeftLine(container.getAsInt(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionDouble(5));
		else if(primitiveCode==4) {
			int N = container.getAsInt(1);
			List<ArithmeticExpressionXY> positions = new ArrayList<ArithmeticExpressionXY>();
			for(int i=0;i<N;i++)
				positions.add(new ArithmeticExpressionXY(container.getAsArithmeticExpressionDouble(2*i+2), container.getAsArithmeticExpressionDouble(2*i+3)));
			macro = new GerberMacroOutline(container.getAsInt(0), positions, container.getAsArithmeticExpressionDouble(2*N+2));
		} else if(primitiveCode==5)
			macro = new GerberMacroPolygon(container.getAsInt(0), container.getAsArithmeticExpressionInt(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionDouble(5));
		else if(primitiveCode==6)
			macro = new GerberMacroMoire(container.getAsArithmeticExpressionDouble(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionInt(5), container.getAsArithmeticExpressionDouble(6), container.getAsArithmeticExpressionDouble(7), container.getAsArithmeticExpressionDouble(8));
		else if(primitiveCode==7)
			macro = new GerberMacroThermal(container.getAsArithmeticExpressionDouble(0), container.getAsArithmeticExpressionDouble(1), container.getAsArithmeticExpressionDouble(2), container.getAsArithmeticExpressionDouble(3), container.getAsArithmeticExpressionDouble(4), container.getAsArithmeticExpressionDouble(5));
		if(macro==null || macro instanceof UnsupportedMacroInterface)
			throw new UnsupportedMacroException(primitiveCode, modifiersStr);
		return macro;
	}
	
	public static class ArithmeticExpression {
		protected String expression;
		public ArithmeticExpression(String expression) {
			this.expression = expression;
		}
		public String toString(){	return expression;	}
	}
	public static class ArithmeticExpressionDouble extends ArithmeticExpression {
		public ArithmeticExpressionDouble(String expression) {
			super(expression);
		}
		public Double value(GerberMacro macro) throws ArithmeticExpressionUnsupportedException {
			if(expression.indexOf("$")<0)
				return parseDouble(expression);
			throw new ArithmeticExpressionUnsupportedException(toString());
		}
	}
	public static class ArithmeticExpressionInt extends ArithmeticExpression {
		public ArithmeticExpressionInt(String expression) {
			super(expression);
		}
		public Integer value(GerberMacro macro) throws ArithmeticExpressionUnsupportedException {
			if(expression.indexOf("$")<0)
				return Integer.parseInt(expression);
			throw new ArithmeticExpressionUnsupportedException(toString());
		}
	}
	
	public static class ModifiersContainer extends ArrayList<String> {
		
		public ModifiersContainer(String sep, String modifiersStr) {
			String[] array = modifiersStr.split(sep);
			for(String val:array)
				add(val);
		}
		
		public ArithmeticExpressionDouble getAsArithmeticExpressionDouble(int index) {
			return (size()<index)?null:new ArithmeticExpressionDouble(get(index));
		}
		public ArithmeticExpressionInt getAsArithmeticExpressionInt(int index) {
			return (size()<index)?null:new ArithmeticExpressionInt(get(index));
		}
		public Double getAsDouble(int index) {
			return (size()<index)?null:parseDouble(get(index));
		}
		public Integer getAsInt(int index) {
			return (size()<index)?null:Integer.parseInt(get(index));
		}
		
		public ApertureModifier getApertureModifier(int index) {
			if(index>=size())return null;
			if(index+1==size())
				return new ApertureModifierHole(parseDouble(get(index)));
			return new ApertureModifierRectanglar(parseDouble(get(index)),parseDouble(get(index+1)));
		}
	}
	public static double parseDouble(String val) {
		if(val.startsWith("."))val = "0" + val;
		return Double.parseDouble(val);
	}
	private static final Pattern PATTERN_XY = Pattern.compile("^X(.+?)Y(.+?)$");
	public static PositionXYInterface parsePosition(String positionStr) throws IllegalPositionException {
		Matcher matcher = PATTERN_XY.matcher(positionStr);
		if(!matcher.find())
			throw new IllegalPositionException(positionStr);
		return new SimpleXY(parseDouble(matcher.group(1)), parseDouble(matcher.group(2)));
	}

}

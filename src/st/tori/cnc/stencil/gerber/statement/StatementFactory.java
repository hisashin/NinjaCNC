package st.tori.cnc.stencil.gerber.statement;

import java.util.ArrayList;

import st.tori.cnc.stencil.gerber.aperture.GerberAperture;
import st.tori.cnc.stencil.gerber.aperture.GerberApertureCircle;
import st.tori.cnc.stencil.gerber.aperture.GerberApertureMacro;
import st.tori.cnc.stencil.gerber.aperture.GerberApertureObround;
import st.tori.cnc.stencil.gerber.aperture.GerberApertureRectangle;
import st.tori.cnc.stencil.gerber.aperture.GerberApertureRegularPolygon;
import st.tori.cnc.stencil.gerber.aperture.UnsupportedApertureInterface;
import st.tori.cnc.stencil.gerber.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.aperture.modifier.ApertureModifierHole;
import st.tori.cnc.stencil.gerber.aperture.modifier.ApertureModifierRectanglar;
import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.exception.ArithmeticExpressionUnsupportedException;
import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedApertureException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedIndexException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedParameterCodeException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

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
	
	public static DStatement createDStatement(int dIndex, Gerber gerber) throws UnsupportedIndexException {
		DStatement statement = null;
		if(dIndex==1)
			statement = new DStatement01(gerber);
		else if(dIndex==2)
			statement = new DStatement02(gerber);
		else if(dIndex==3)
			statement = new DStatement03(gerber);
		else if(dIndex>=10)
			statement = new DStatement10orHigher(gerber);
		if(statement==null || statement instanceof UnsupportedStatementInterface)
			throw new UnsupportedIndexException("D",dIndex);
		return statement;
	}

	public static PStatement createPStatement(String parameterCode, String modifiers, Gerber gerber) throws UnsupportedParameterCodeException, IllegalParameterModifiersException, ArithmeticExpressionUnsupportedException {
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
		ModifiersContainer container = new ModifiersContainer(modifiersStr);
		if("C".equals(type))
			aperture = new GerberApertureCircle(dcode, container.getAsDouble(0), container.getModifier(1), gerber);
		else if("R".equals(type))
			aperture = new GerberApertureRectangle(dcode, container.getAsDouble(0), container.getAsDouble(1), container.getModifier(2), gerber);
		else if("O".equals(type))
			aperture = new GerberApertureObround(dcode, container.getAsDouble(0), container.getAsDouble(1), container.getModifier(2), gerber);
		else if("P".equals(type))
			aperture = new GerberApertureRegularPolygon(dcode, container.getAsDouble(0), container.getAsInt(1), container.getAsDouble(2), container.getModifier(3), gerber);
		else
			aperture = new GerberApertureMacro(dcode, type, gerber);
		if(aperture==null || aperture instanceof UnsupportedApertureInterface)
			throw new UnsupportedApertureException(dcode, type, modifiersStr);
		return aperture;
	}
	private static class ModifiersContainer extends ArrayList<String> {
		
		public ModifiersContainer(String modifiersStr) {
			String[] array = modifiersStr.split("X");
			for(String val:array)
				add(val);
		}
		
		public Double getAsDouble(int index) {
			return (size()<index)?null:parseDouble(get(index));
		}
		public Integer getAsInt(int index) {
			return (size()<index)?null:Integer.parseInt(get(index));
		}
		
		public ApertureModifier getModifier(int index) {
			if(index>=size())return null;
			if(index+1==size())
				return new ApertureModifierHole(parseDouble(get(index)));
			return new ApertureModifierRectanglar(parseDouble(get(index)),parseDouble(get(index+1)));
		}
	}
	private static double parseDouble(String val) {
		if(val.startsWith("."))val = "0" + val;
		return Double.parseDouble(val);
	}

}

package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
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

	public static PStatement createPStatement(String parameterCode, String modifiers, Gerber gerber) throws UnsupportedParameterCodeException, IllegalParameterModifiersException {
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
		if(statement==null || statement instanceof UnsupportedStatementInterface)
			throw new UnsupportedParameterCodeException(parameterCode,modifiers);
		return statement;
	}

}

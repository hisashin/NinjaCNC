package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalParameterModifiersException;
import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedIndexException;
import st.tori.cnc.stencil.gerber.exception.UnsupportedParameterCodeException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public class StatementFactory {

	public static GStatement createGStatement(int gIndex, Gerber gerber) throws UnsupportedIndexException, NoLastStatementExistsException, IllegalReflectionException {
		GStatement action;
		if(gIndex<0)
			action = gerber.cloneLastStatement();
		else if(gIndex==1)
			action = new GStatement01(gerber);
		else if(gIndex==2)
			action = new GStatement02(gerber);
		else if(gIndex==3)
			action = new GStatement03(gerber);
		else if(gIndex==4)
			action = new GStatement04(gerber);
		else if(gIndex==36)
			action = new GStatement36(gerber);
		else if(gIndex==37)
			action = new GStatement37(gerber);
		else if(gIndex==74)
			action = new GStatement74(gerber);
		else if(gIndex==75)
			action = new GStatement75(gerber);
		else
			throw new UnsupportedIndexException("G",gIndex);
		return action;
	}
	
	public static DStatement createDStatement(int dIndex, Gerber gerber) throws UnsupportedIndexException {
		if(dIndex==1)
			return new DStatement01(gerber);
		else if(dIndex==2)
			return new DStatement02(gerber);
		else if(dIndex==3)
			return new DStatement03(gerber);
		else if(dIndex>=10)
			return new DStatement10orHigher(gerber);
		throw new UnsupportedIndexException("D",dIndex);
	}

	public static PStatement createPStatement(String parameterCode, String modifiers, Gerber gerber) throws UnsupportedParameterCodeException, IllegalParameterModifiersException {
		if("FS".equals(parameterCode))
			return new PStatementFS(modifiers, gerber);
		else if("MO".equals(parameterCode))
			return new PStatementMO(modifiers, gerber);
		else if("IP".equals(parameterCode))
			return new PStatementIP(modifiers, gerber);
		else if("AD".equals(parameterCode))
			return new PStatementAD(modifiers, gerber);
		else if("AM".equals(parameterCode))
			return new PStatementAM(modifiers, gerber);
		else if("SR".equals(parameterCode))
			return new PStatementSR(modifiers, gerber);
		else if("LP".equals(parameterCode))
			return new PStatementLP(modifiers, gerber);
		throw new UnsupportedParameterCodeException(parameterCode,modifiers);
	}

}

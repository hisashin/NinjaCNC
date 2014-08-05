package st.tori.cnc.stencil.gerber.statement;

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

	public static MStatement createMStatement(int mIndex) throws UnsupportedIndexException {
		if(mIndex==2)
			return new MStatement02();
		throw new UnsupportedIndexException("M",mIndex);
	}

	public static PStatement createPStatement(String parameterCode, String modifiers) throws UnsupportedParameterCodeException {
		if("FS".equals(parameterCode))
			return new PStatementFS(modifiers);
		else if("MO".equals(parameterCode))
			return new PStatementMO(modifiers);
		else if("IP".equals(parameterCode))
			return new PStatementIP(modifiers);
		else if("AD".equals(parameterCode))
			return new PStatementAD(modifiers);
		else if("AM".equals(parameterCode))
			return new PStatementAM(modifiers);
		else if("SR".equals(parameterCode))
			return new PStatementSR(modifiers);
		else if("LP".equals(parameterCode))
			return new PStatementLP(modifiers);
		throw new UnsupportedParameterCodeException(parameterCode,modifiers);
	}

}

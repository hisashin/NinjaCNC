package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.InvalidIndexException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public class StatementFactory {

	public static GStatement createGStatement(int gIndex, Gerber gerber) throws InvalidIndexException, NoLastStatementExistsException, IllegalReflectionException {
		GStatement action;
		if(gIndex<0) {
			action = gerber.cloneLastStatement();
		}else if(gIndex==2) {
			action = new GStatement02(gerber);
		}else if(gIndex==3) {
			action = new GStatement03(gerber);
		}else if(gIndex==36) {
			action = new GStatement36(gerber);
		}else if(gIndex==37) {
			action = new GStatement37(gerber);
		}else if(gIndex==75)
			action = new GStatement75(gerber);
		else
			throw new InvalidIndexException("G",gIndex);
		return action;
	}
	
	public static MStatement createMStatement(int mIndex) throws InvalidIndexException {
		if(mIndex==2)return new MStatement02();
		throw new InvalidIndexException("M",mIndex);
	}

}

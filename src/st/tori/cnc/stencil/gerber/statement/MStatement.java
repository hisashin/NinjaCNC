package st.tori.cnc.stencil.gerber.statement;

import st.tori.cnc.stencil.util.NumberUtil;

public abstract class MStatement implements StatementInterface {

	protected abstract int getMIndex();

	@Override
	public final String getSimpleName() {
		return "M"+NumberUtil.toString(getMIndex(), 2)+"*";
	}

}

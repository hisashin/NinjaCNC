package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.util.NumberUtil;

public abstract class MAction implements ActionInterface {

	protected abstract int getMIndex();

	@Override
	public final String getSimpleName() {
		return "M"+NumberUtil.toString(getMIndex(), 2);
	}

}

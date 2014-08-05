package st.tori.cnc.stencil.gerber.exception;

import st.tori.cnc.stencil.util.NumberUtil;

public class UnsupportedIndexException extends GerberException {

	public UnsupportedIndexException(String prefix, int gIndex) {
		super(prefix+NumberUtil.toString(gIndex, 2)+" is not yet supported.would you?");
	}
	
}

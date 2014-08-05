package st.tori.cnc.stencil.gcode.exception;

import st.tori.cnc.stencil.util.NumberUtil;

public class UnsupportedIndexException extends GCodeException {

	public UnsupportedIndexException(String prefix, int gIndex) {
		super(prefix+NumberUtil.toString(gIndex, 2)+" is not yet supported.would you?");
	}
	
}

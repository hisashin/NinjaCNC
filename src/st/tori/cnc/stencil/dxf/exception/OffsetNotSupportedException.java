package st.tori.cnc.stencil.dxf.exception;

import java.util.List;

import st.tori.cnc.stencil.canvas.PositionXYInterface;

public class OffsetNotSupportedException extends DxfException {

	private List<PositionXYInterface> xyList;
	
	public OffsetNotSupportedException(List<PositionXYInterface> xyList) {
		super("Offset not yet supported for "+toString(xyList));
		this.xyList = xyList;
	}
	
	public static String toString(List<PositionXYInterface> xyList) {
		if(xyList==null)return "NULL";
		if(xyList.size()<=0)return "NO POINT";
		StringBuffer buf = new StringBuffer();
		for(PositionXYInterface position: xyList)
			buf.append("[").append(position.getX()).append(",").append(position.getY()).append("]");
		return buf.toString();
	}

}

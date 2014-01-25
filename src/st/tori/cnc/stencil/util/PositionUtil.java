package st.tori.cnc.stencil.util;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.PositionXYZInterface;

public class PositionUtil {
	
	public static boolean isDistantXY(PositionXYInterface p0, PositionXYInterface p1) {
		return (p0.getX()!=p1.getX()||p0.getY()!=p1.getY());
	}
	public static boolean isDistantZ(PositionXYZInterface p0, PositionXYZInterface p1) {
		return (p0.getZ()!=p1.getZ());
	}
	public static boolean isDistantXYZ(PositionXYZInterface p0, PositionXYZInterface p1) {
		return (isDistantXY(p0, p1)||isDistantZ(p0, p1));
	}
	public static boolean isCutting(PositionXYZInterface p0, PositionXYZInterface p1) {
		return (isDistantXY(p0, p1)&&p0.getZ()<0&&p1.getZ()<0);
	}
	
}

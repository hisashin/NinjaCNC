package st.tori.cnc.stencil.util;

import java.util.List;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;

public class PositionUtil {
	
	public static boolean isSameXY(List<PositionXYInterface> xyList) {
		PositionXYInterface lastPosition = null;
		for(PositionXYInterface position: xyList) {
			if(lastPosition!=null&&isDistantXY(lastPosition, position))return false;
			lastPosition = position;
		}
		return true;
	}
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
	public static PositionXYInterface getGravityPoint(List<PositionXYInterface> xyList) {
		if(xyList==null||xyList.size()<=0)return null;
		double sumX = 0.0;
		double sumY = 0.0;
		for(PositionXYInterface position: xyList) {
			sumX += position.getX();
			sumY += position.getY();
		}
		return new SimpleXY(sumX/xyList.size(), sumY/xyList.size());
	}
	public static boolean contains(List<PositionXYInterface> xyList, PositionXYInterface position) {
		if(xyList==null||xyList.size()<=0)return false;
		for(PositionXYInterface _position: xyList) {
			if(isDistantXY(_position, position))continue;
			return true;
		}
		return false;
	}
	
	public static double getVectorLength(PositionXYInterface vector) {
		return Math.sqrt(vector.getX()*vector.getX()+vector.getY()*vector.getY());
	}
	public static PositionXYInterface getUnitVector(PositionXYInterface vector) {
		if(vector==null)return null;
		double len = getVectorLength(vector);
		return new SimpleXY((len==0.0)?0.0:vector.getX()/len, (len==0.0)?0.0:vector.getY()/len);
	}
	public static PositionXYInterface getDiffVector(PositionXYInterface origin, PositionXYInterface target, boolean asUnitVector) {
		PositionXYInterface diff = new SimpleXY(target.getX()-origin.getX(), target.getY()-origin.getY());
		return (asUnitVector)?getUnitVector(diff):diff;
	}
	public static PositionXYInterface getMergedVector(PositionXYInterface origin, PositionXYInterface target, boolean asUnitVector) {
		PositionXYInterface diff = new SimpleXY(target.getX()+origin.getX(), target.getY()+origin.getY());
		return (asUnitVector)?getUnitVector(diff):diff;
	}
	public static double getNarrowAngleBetween(PositionXYInterface vec1, PositionXYInterface vec2) {
		return Math.acos((vec1.getX()*vec2.getX()+vec1.getY()*vec2.getY())/(getVectorLength(vec1)*getVectorLength(vec2)));
	}
	
}

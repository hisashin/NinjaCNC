package st.tori.cnc.stencil.gcode.action;

public interface PositionInterface {

	void setX(double x);
	void setY(double y);
	void setZ(double z);
	
	double getX();
	double getY();
	double getZ();
	
}

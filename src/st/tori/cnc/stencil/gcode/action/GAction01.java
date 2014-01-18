package st.tori.cnc.stencil.gcode.action;

import st.tori.cnc.stencil.gcode.parser.SpeedInterface;

/*
	G01
	Linear interpolation
	M	T
	The most common workhorse code for feeding during a cut.
	The program specs the start and end points, and the control automatically 
	calculates (interpolates) the intermediate points to pass through 
	that will yield a straight line (hence "linear"). 
	The control then calculates the angular velocities at which to turn the axis 
	leadscrews via their servomotors or stepper motors. The computer performs 
	thousands of calculations per second, and the motors react quickly to each input. 
	Thus the actual toolpath of the machining takes place with the given feedrate 
	on a path that is accurately linear to within very small limits.
 */
public class GAction01 extends GAction implements PositionXYZInterface,SpeedInterface {

	@Override
	protected int getGIndex() {	return 1;	}

	public GAction01(GCode gCode) {
		super(gCode);
	}
	protected double x;
	protected double y;
	protected double z;
	protected double f;
	
	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public void setF(double f) {
		this.f = f;
	}


	@Override
	public double getX(){	return x;	}
	@Override
	public double getY(){	return y;	}
	@Override
	public double getZ(){	return z;	}
	@Override
	public double getF(){	return f;	}
}

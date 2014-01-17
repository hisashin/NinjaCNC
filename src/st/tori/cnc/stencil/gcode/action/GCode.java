package st.tori.cnc.stencil.gcode.action;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;
import st.tori.cnc.stencil.gcode.parser.SpeedInterface;


public class GCode extends ArrayList<ActionInterface> {

	public enum UNIT {
		UNDEF,
		INCH,
		MM,
	}
	public enum PROG {
		UNDEF,
		ABSOLUTE,
		INCREMENTAL,
	}
	public enum SPINDLE {
		OFF,
		ON,
	}
	
	private UNIT unit = UNIT.UNDEF;
	private PROG prog = PROG.UNDEF;
	private SPINDLE spindle = SPINDLE.OFF;
	
	private PositionInterface lastPosition = null;
	private SpeedInterface lastSpeed = null;
	
	public PositionInterface getLastPosition(){	return lastPosition;	}
	public SpeedInterface getLastSpeed(){	return lastSpeed;	}
	
	protected final void isReadyToMove() throws NoSpecifiedUnitException, NoSpecifiedProgramException {
		if(unit==UNIT.UNDEF)throw new NoSpecifiedUnitException();
		if(prog==PROG.UNDEF)throw new NoSpecifiedProgramException();
	}
	protected final boolean isSpindleOn(){
		return (spindle==SPINDLE.ON);
	}
	public boolean isEqual(GCode code) {
		return false;
	}
	
	@Override
	public final boolean add(ActionInterface action) {
		if(action instanceof GAction20) {
			unit = UNIT.INCH;
		}else if(action instanceof GAction21){
			unit = UNIT.MM;
		}else if(action instanceof GAction90) {
			prog = PROG.ABSOLUTE;
		}else if(action instanceof GAction91){
			prog = PROG.INCREMENTAL;
		}else if(action instanceof MAction03) {
			spindle = SPINDLE.ON;
		}else if(action instanceof MAction30) {
			spindle = SPINDLE.OFF;
		}
		if(action instanceof PositionInterface)
			lastPosition = (PositionInterface)action;
		if(action instanceof SpeedInterface)
			lastSpeed = (SpeedInterface)action;
		return super.add(action);
	}
	
}

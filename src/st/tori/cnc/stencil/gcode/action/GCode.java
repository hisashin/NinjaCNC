package st.tori.cnc.stencil.gcode.action;

import java.util.ArrayList;

import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;


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
	
	protected final void isReadyToMove() throws NoSpecifiedUnitException, NoSpecifiedProgramException {
		if(unit==UNIT.UNDEF)throw new NoSpecifiedUnitException();
		if(prog==PROG.UNDEF)throw new NoSpecifiedProgramException();
	}
	protected final boolean isSpindleOn(){
		return (spindle==SPINDLE.ON);
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
		return super.add(action);
	}
	
}

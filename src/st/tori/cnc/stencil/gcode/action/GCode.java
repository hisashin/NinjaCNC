package st.tori.cnc.stencil.gcode.action;

import java.util.ArrayList;
import java.util.Iterator;

import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;
import st.tori.cnc.stencil.gcode.parser.SpeedInterface;
import st.tori.cnc.stencil.util.NumberUtil;


public class GCode extends ArrayList<ActionInterface> {

	private boolean initialized = false;
	private boolean finalized = false;
	
	private double initialAirCutHeight = Double.MAX_VALUE;
	private double airCutHeight = Double.MAX_VALUE;
	public double getAirCutHeight(){	return airCutHeight;	}
	
	public final void initialize(double initialAirCutHeight , double airCutHeight) {
		initialize(UNIT.MM, PROG.ABSOLUTE, initialAirCutHeight, airCutHeight);
	}
	public final void initialize(UNIT unit, PROG prog, double initialAirCutHeight , double airCutHeight) {
		initialized = true;
		this.initialAirCutHeight = initialAirCutHeight;
		this.airCutHeight = airCutHeight;
		add(new Comment("Header"));
		if(unit==UNIT.MM)
			add(new GAction21(this));
		else if(unit==UNIT.INCH)
			add(new GAction20(this));
		//Set exact stop mode without mind
		add(new GAction61(this));
		if(prog==PROG.ABSOLUTE)
			add(new GAction90(this));
		else if(prog==PROG.INCREMENTAL)
			add(new GAction91(this));
		//Move to origin,at height of initialAirCutHeight
		{
			GAction00 action = new GAction00(this);
			action.setX(0);
			action.setY(0);
			action.setZ(initialAirCutHeight);
			add(action);
		}
		//Spindle on
		add(new MAction03());
		//Close to the surface at height of airCutHeight
		{
			GAction00 action = new GAction00(this);
			action.setZ(airCutHeight);
			add(action);
		}
	}
	public final void finalize() {
		if(!initialized)return;
		add(new Comment("Footer"));
		add(new GAction61(this));
		{
			GAction00 action = new GAction00(this);
			action.setX(0);
			action.setY(0);
			action.setZ(initialAirCutHeight);
			add(action);
		}
		add(new MAction30());
		finalized = true;
	}
	
	private static final String RET = "\n";
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator<ActionInterface> ite = super.iterator();
		boolean zInitialized = false;
		GAction lastAction = null;
		boolean zChanged = true;
		PositionInterface lastPosition = null;
		SpeedInterface lastSpeed = null;
		while(ite.hasNext()) {
			ActionInterface action = ite.next();
			if(buf.length()>0 && (!(action instanceof GAction) || !((GAction)action).isFundamental()))
				buf.append(RET);
			if(action instanceof GAction) {
				GAction gAction = (GAction)action;
				if(lastAction == null || lastAction.getGIndex() != gAction.getGIndex()) {
					//Write G when gIndex changed
					buf.append(action.getSimpleName());
				}else if(zChanged) {
					//Write G just after Z changed
					buf.append(action.getSimpleName());
					zChanged = false;
				}else if(gAction instanceof PositionInterface 
						&& lastPosition != null 
						&& ((PositionInterface)gAction).getZ() != lastPosition.getZ()) {
					//Skip G if Z unchanged
					buf.append(action.getSimpleName());
				}
				lastAction = gAction;
			}else{
				buf.append(action.getSimpleName());
			}
			if(action instanceof PositionInterface) {
				PositionInterface position = (PositionInterface)action;
				if(!zInitialized) {
					if(lastPosition==null||lastPosition.getZ()!=position.getZ()) {
						buf.append("Z").append(NumberUtil.toGCodeValue(position.getZ())).append(RET);
						buf.append(action.getSimpleName());
						zChanged = true;
					}
					if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
						buf.append("X").append(NumberUtil.toGCodeValue(position.getX()));
						buf.append("Y").append(NumberUtil.toGCodeValue(position.getY()));
					}
					//zInitialized = true;
				}else{
					if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
						buf.append("X").append(NumberUtil.toGCodeValue(position.getX()));
						buf.append("Y").append(NumberUtil.toGCodeValue(position.getY()));
					}
					if(lastPosition==null||lastPosition.getZ()!=position.getZ()) {
						buf.append("Z").append(NumberUtil.toGCodeValue(position.getZ()));
						zChanged = true;
					}
				}
				lastPosition = position;
			}
			if(action instanceof SpeedInterface) {
				SpeedInterface speed = (SpeedInterface)action;
				if(lastSpeed==null||lastSpeed.getF()!=speed.getF())
					buf.append("F").append(NumberUtil.toGCodeValue(speed.getF()));
				lastSpeed = speed;
			}
		}
		buf.append(RET);
		return buf.toString();
	}
	
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
	
	private GAction lastAction = null;
	private PositionInterface lastPosition = null;
	private SpeedInterface lastSpeed = null;
	
	public GAction getLastAction(){	return lastAction;	}
	public PositionInterface getLastPosition(){	return lastPosition;	}
	public SpeedInterface getLastSpeed(){	return lastSpeed;	}
	
	public GAction cloneLastAction() throws NoLastActionExistsException, IllegalReflectionException {
		if(lastAction==null)
			throw new NoLastActionExistsException();
		try {
			return (GAction)(lastAction.getClass().getConstructor(getClass()).newInstance(this));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalReflectionException(e);
		}
	}

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
		if(action instanceof GAction)
			lastAction = (GAction)action;
		if(action instanceof PositionInterface)
			lastPosition = (PositionInterface)action;
		if(action instanceof SpeedInterface)
			lastSpeed = (SpeedInterface)action;
		return super.add(action);
	}
	
}

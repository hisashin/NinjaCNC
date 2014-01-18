package st.tori.cnc.stencil.gcode.action;

import java.util.ArrayList;
import java.util.Iterator;

import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;
import st.tori.cnc.stencil.gcode.parser.SpeedInterface;
import st.tori.cnc.stencil.gcode.shape.ShapePolygon;
import st.tori.cnc.stencil.util.NumberUtil;


public class GCode extends ArrayList<ActionInterface> {

	private boolean initialized = false;
	private boolean finalized = false;
	
	private double initialAirCutHeight = Double.MAX_VALUE;
	private double airCutHeight = Double.MAX_VALUE;
	private double cutHeight = Double.MAX_VALUE;
	private double downSpeed = 0;
	private double cutSpeed = 0;
	
	public double getAirCutHeight(){	return airCutHeight;	}
	public double getCutHeight(){	return cutHeight;	}
	public double getDownSpeed(){	return downSpeed;	}
	public double getCutSpeed(){	return cutSpeed;	}
	
	public final void initialize(double initialAirCutHeight , double airCutHeight, double cutHeight, double downSpeed, double cutSpeed) {
		initialize(UNIT.MM, PROG.ABSOLUTE, initialAirCutHeight, airCutHeight, cutHeight, downSpeed, cutSpeed);
	}
	public final void initialize(UNIT unit, PROG prog, double initialAirCutHeight , double airCutHeight, double cutHeight, double downSpeed, double cutSpeed) {
		initialized = true;
		this.initialAirCutHeight = initialAirCutHeight;
		this.airCutHeight = airCutHeight;
		this.cutHeight = cutHeight;
		this.downSpeed = downSpeed;
		this.cutSpeed = cutSpeed;
		
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
		boolean zChanged = false;
		GAction lastAction = null;
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
				zChanged = false;
				if(lastPosition==null||lastPosition.getZ()!=position.getZ()) {
					buf.append("Z").append(NumberUtil.toGCodeValue(position.getZ()));
					zChanged = true;
				}
				if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
					if(zChanged)
						buf.append(RET).append(action.getSimpleName());
					buf.append("X").append(NumberUtil.toGCodeValue(position.getX()));
					buf.append("Y").append(NumberUtil.toGCodeValue(position.getY()));
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
	
	public final boolean add(ShapePolygon polygon) {
		double[][] array = polygon.getArray();
		{
			GAction00 action = new GAction00(this);
			action.setZ(getAirCutHeight());
			action.setX(array[0][0]);
			action.setY(array[0][1]);
			add(action);
		}
		{
			GAction01 action = new GAction01(this);
			action.setZ(getCutHeight());
			action.setF(getDownSpeed());
			add(action);
		}
		for (int i = 1; i < array.length; i++) {
			GAction01 action = new GAction01(this);
			action.setX(array[i][0]);
			action.setY(array[i][1]);
			action.setZ(getCutHeight());
			action.setF(getCutSpeed());
			add(action);
		}
		{
			GAction00 action = new GAction00(this);
			action.setZ(getAirCutHeight());
			add(action);
		}
		return true;
	}
	
}

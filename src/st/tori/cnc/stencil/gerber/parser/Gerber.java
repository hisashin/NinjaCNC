package st.tori.cnc.stencil.gerber.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.shape.Polygon;
import st.tori.cnc.stencil.gcode.statement.StatementInterface;
import st.tori.cnc.stencil.gcode.statement.Comment;
import st.tori.cnc.stencil.gcode.statement.MStatement03;
import st.tori.cnc.stencil.gcode.statement.MStatement30;
import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;
import st.tori.cnc.stencil.gcode.parser.GCode;
import st.tori.cnc.stencil.gcode.parser.SpeedInterface;
import st.tori.cnc.stencil.gerber.statement.GStatement;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.util.NumberUtil;


public class Gerber extends ArrayList<StatementInterface> {

	private static final String RET = "\n";

	private boolean initialized = false;
	private boolean finalized = false;
	
	public final void initialize() {
		initialize();
	}
	public final void initialize() {
		initialized = true;
		
		if(unit==UNIT.MM)
			add(new GStatement21(this));
		else if(unit==UNIT.INCH)
			add(new GStatement20(this));
		//Set exact stop mode without mind
		add(new GStatement61(this));
		if(prog==PROG.ABSOLUTE)
			add(new GStatement90(this));
		else if(prog==PROG.INCREMENTAL)
			add(new GStatement91(this));
		//Move to origin,at height of initialAirCutHeight
		{
			GStatement00 statement = new GStatement00(this);
			statement.setX(0);
			statement.setY(0);
			statement.setZ(initialAirCutHeight);
			add(statement);
		}
		//Spindle on
		add(new MStatement03());
		//Close to the surface at height of airCutHeight
		{
			GStatement00 statement = new GStatement00(this);
			statement.setZ(airCutHeight);
			add(statement);
		}
	}
	public final void finalize() {
		if(!initialized)return;
		add(new Comment("Footer"));
		add(new GStatement61(this));
		{
			GStatement00 statement = new GStatement00(this);
			statement.setX(0);
			statement.setY(0);
			statement.setZ(initialAirCutHeight);
			add(statement);
		}
		add(new MStatement30());
		finalized = true;
	}
		
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator<StatementInterface> ite = super.iterator();
		boolean zChanged = false;
		StatementInterface lastStatement = null;
		GStatement lastGStatement = null;
		PositionXYZInterface lastPosition = null;
		SpeedInterface lastSpeed = null;
		while(ite.hasNext()) {
			StatementInterface statement = ite.next();
			if((lastStatement!=null&&lastStatement instanceof Comment)
				||(buf.length()>0 
						&& (!(statement instanceof GStatement) 
								|| (!((GStatement)statement).isFundamental())) 
								|| (lastGStatement!=null&&!lastGStatement.isFundamental())))
				buf.append(RET);
			if(statement instanceof GStatement) {
				GStatement gStatement = (GStatement)statement;
				if(lastGStatement == null || lastGStatement.getGIndex() != gStatement.getGIndex()) {
					//Write G when gIndex changed
					buf.append(statement.getSimpleName());
				}else if(zChanged
						&& lastPosition !=null
						&& (((PositionXYZInterface)gStatement).getX() != lastPosition.getX()
							||((PositionXYZInterface)gStatement).getY() != lastPosition.getY())) {
					//Write G just after Z changed and XY will change
					buf.append(statement.getSimpleName());
				}else if(gStatement instanceof PositionXYZInterface 
						&& lastPosition != null 
						&& ((PositionXYZInterface)gStatement).getZ() != lastPosition.getZ()) {
					//Skip G if Z unchanged
					buf.append(statement.getSimpleName());
				}
				lastGStatement = gStatement;
			}else{
				buf.append(statement.getSimpleName());
			}
			if(statement instanceof PositionXYZInterface) {
				PositionXYZInterface position = (PositionXYZInterface)statement;
				zChanged = false;
				if(lastPosition==null||lastPosition.getZ()!=position.getZ()) {
					buf.append("Z").append(NumberUtil.toGCodeValue(position.getZ()));
					zChanged = true;
				}
				if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
					if(zChanged) {
						buf.append(RET).append(statement.getSimpleName());
					}
					buf.append("X").append(NumberUtil.toGCodeValue(position.getX()));
					buf.append("Y").append(NumberUtil.toGCodeValue(position.getY()));
				}
				lastPosition = position;
			}
			if(statement instanceof SpeedInterface) {
				SpeedInterface speed = (SpeedInterface)statement;
				if(lastSpeed==null||lastSpeed.getF()!=speed.getF())
					buf.append("F").append(NumberUtil.toGCodeValue(speed.getF()));
				lastSpeed = speed;
			}
			lastStatement = statement;
		}
		buf.append(RET);
		//Remove ret+ret which inserts around first Z-XY positioning
		return buf.toString().replaceAll(RET+RET+"?", RET);
	}
	
	public enum INTERPOLATION_MODE {
		UNDEF,
		LINER,
		CLOCKWISE_CIRCULAR,
		COUNTERCLOCKWISE_CIRCULAR,
	}
	public enum REGION_MODE {
		UNDEF,
		ON,
		OFF,
	}
	public enum QUADRANT_MODE {
		UNDEF,
		SINGLE,
		MULTI,
	}
	
	private UNIT unit = UNIT.UNDEF;
	private PROG prog = PROG.UNDEF;
	private SPINDLE spindle = SPINDLE.OFF;
	
	private GStatement lastStatement = null;
	private PositionXYZInterface lastPosition = null;
	private SpeedInterface lastSpeed = null;
	
	public GStatement getLastStatement(){	return lastStatement;	}
	public PositionXYZInterface getLastPosition(){	return lastPosition;	}
	public SpeedInterface getLastSpeed(){	return lastSpeed;	}
	
	public GStatement cloneLastStatement() throws NoLastStatementExistsException, IllegalReflectionException {
		if(lastStatement==null)
			throw new NoLastStatementExistsException();
		try {
			return (GStatement)(lastStatement.getClass().getConstructor(getClass()).newInstance(this));
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
	public final boolean add(StatementInterface statement) {
		if(statement instanceof GStatement20) {
			unit = UNIT.INCH;
		}else if(statement instanceof GStatement21){
			unit = UNIT.MM;
		}else if(statement instanceof GStatement90) {
			prog = PROG.ABSOLUTE;
		}else if(statement instanceof GStatement91){
			prog = PROG.INCREMENTAL;
		}else if(statement instanceof MStatement03) {
			spindle = SPINDLE.ON;
		}else if(statement instanceof MStatement30) {
			spindle = SPINDLE.OFF;
		}
		if(statement instanceof GStatement)
			lastStatement = (GStatement)statement;
		if(statement instanceof PositionXYZInterface)
			lastPosition = (PositionXYZInterface)statement;
		if(statement instanceof SpeedInterface)
			lastSpeed = (SpeedInterface)statement;
		return super.add(statement);
	}
	
	private List<Drawable> drawables = new ArrayList<Drawable>();
	
	public final boolean add(Drawable drawable) {
		return drawables.add(drawable);
	}

}

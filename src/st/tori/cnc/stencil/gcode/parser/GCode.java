package st.tori.cnc.stencil.gcode.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;
import st.tori.cnc.stencil.canvas.shape.Line;
import st.tori.cnc.stencil.canvas.shape.Polygon;
import st.tori.cnc.stencil.gcode.action.ActionInterface;
import st.tori.cnc.stencil.gcode.action.Comment;
import st.tori.cnc.stencil.gcode.action.GAction;
import st.tori.cnc.stencil.gcode.action.GAction00;
import st.tori.cnc.stencil.gcode.action.GAction01;
import st.tori.cnc.stencil.gcode.action.GAction20;
import st.tori.cnc.stencil.gcode.action.GAction21;
import st.tori.cnc.stencil.gcode.action.GAction61;
import st.tori.cnc.stencil.gcode.action.GAction90;
import st.tori.cnc.stencil.gcode.action.GAction91;
import st.tori.cnc.stencil.gcode.action.MAction03;
import st.tori.cnc.stencil.gcode.action.MAction30;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedProgramException;
import st.tori.cnc.stencil.gcode.exception.NoSpecifiedUnitException;
import st.tori.cnc.stencil.util.NumberUtil;
import st.tori.cnc.stencil.util.PositionUtil;



public class GCode extends ArrayList<ActionInterface> implements Drawable {

	private static final String RET = "\n";

	private Drill drill;
	private double initialAirCutHeight = Double.MAX_VALUE;
	private double airCutHeight = Double.MAX_VALUE;
	private double cutHeight = Double.MAX_VALUE;
	private double downSpeed = 0;
	private double cutSpeed = 0;
	
	public Drill getDrill(){	return drill;	}
	public double getAirCutHeight(){	return airCutHeight;	}
	public double getCutHeight(){	return cutHeight;	}
	public double getDownSpeed(){	return downSpeed;	}
	public double getCutSpeed(){	return cutSpeed;	}
	
	private boolean initialized = false;
	private boolean finalized = false;
	
	public GCode(Drill drill) {
		this.drill = drill;
	}
	
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
		
		double diameter = drill.getDiameter(-cutHeight);
		System.out.println("Diameter at Z="+cutHeight+"mm is "+NumberUtil.toGCodeValue(diameter)+"mm(radius "+NumberUtil.toGCodeValue(diameter/2)+"mm)");
		
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
		
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator<ActionInterface> ite = super.iterator();
		boolean zChanged = false;
		ActionInterface lastAction = null;
		GAction lastGAction = null;
		PositionXYZInterface lastPosition = null;
		SpeedInterface lastSpeed = null;
		while(ite.hasNext()) {
			ActionInterface action = ite.next();
			if((lastAction!=null&&lastAction instanceof Comment)
				||(buf.length()>0 
						&& (!(action instanceof GAction) 
								|| (!((GAction)action).isFundamental())) 
								|| (lastGAction!=null&&!lastGAction.isFundamental())))
				buf.append(RET);
			if(action instanceof GAction) {
				GAction gAction = (GAction)action;
				if(lastGAction == null || lastGAction.getGIndex() != gAction.getGIndex()) {
					//Write G when gIndex changed
					buf.append(action.getSimpleName());
				}else if(zChanged
						&& lastPosition !=null
						&& PositionUtil.isDistantXY(lastPosition, (PositionXYInterface)gAction)) {
					//Write G just after Z changed and XY will change
					buf.append(action.getSimpleName());
				}else if(gAction instanceof PositionXYZInterface 
						&& lastPosition != null 
						&& ((PositionXYZInterface)gAction).getZ() != lastPosition.getZ()) {
					//Skip G if Z unchanged
					buf.append(action.getSimpleName());
				}
				lastGAction = gAction;
			}else{
				buf.append(action.getSimpleName());
			}
			if(action instanceof PositionXYZInterface) {
				PositionXYZInterface position = (PositionXYZInterface)action;
				zChanged = false;
				if(lastPosition==null||lastPosition.getZ()!=position.getZ()) {
					buf.append("Z").append(NumberUtil.toGCodeValue(position.getZ()));
					zChanged = true;
				}
				if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
					if(zChanged) {
						buf.append(RET).append(action.getSimpleName());
					}
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
			lastAction = action;
		}
		buf.append(RET);
		//Remove ret+ret which inserts around first Z-XY positioning
		return buf.toString().replaceAll(RET+RET+"?", RET);
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
	private PositionXYZInterface lastPosition = null;
	private SpeedInterface lastSpeed = null;
	
	public GAction getLastAction(){	return lastAction;	}
	public PositionXYZInterface getLastPosition(){	return lastPosition;	}
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
		if(action instanceof PositionXYZInterface) {
			if(lastPosition!=null && PositionUtil.isCutting(lastPosition, (PositionXYZInterface)action)) {
				drawables.add(new Line(lastPosition, (PositionXYInterface)action,
						drill.getDiameter(-((PositionXYZInterface)action).getZ())));
				
			}
			lastPosition = (PositionXYZInterface)action;
		}
		if(action instanceof SpeedInterface)
			lastSpeed = (SpeedInterface)action;
		return super.add(action);
	}
	
	private List<Drawable> drawables = new ArrayList<Drawable>();
	@Override
	public void draw(DimensionController dc) {
		Iterator<Drawable> ite = drawables.iterator();
		while(ite.hasNext())
			ite.next().draw(dc);
	}
	
	@Override
	public PositionXYInterface[] getXYMinMax() {
		if(drawables.size()<=0)return null;
		PositionXYInterface minX = null;
		PositionXYInterface minY = null;
		PositionXYInterface maxX = null;
		PositionXYInterface maxY = null;
		for(int i=0;i<drawables.size();i++) {
			PositionXYInterface[] xyMinMax = drawables.get(i).getXYMinMax();
			if(xyMinMax==null)continue;
			if(minX==null||xyMinMax[0].getX()<minX.getX())minX = xyMinMax[0];
			if(minY==null||xyMinMax[0].getY()<minY.getY())minY = xyMinMax[0];
			if(maxX==null||xyMinMax[1].getX()>maxX.getX())maxX = xyMinMax[1];
			if(maxY==null||xyMinMax[1].getY()>maxY.getY())maxY = xyMinMax[1];
		}
		if(minX==null||minY==null||maxX==null||maxY==null)return null;
		return new PositionXYInterface[]{
			new SimpleXY(minX.getX(), minY.getY()),
			new SimpleXY(maxX.getX(), maxY.getY()),
		};
	}
	
	public final boolean add(Drawable drawable) {
		return drawables.add(drawable);
	}
	public final boolean add(Polygon polygon) {
		PositionXYInterface[] array = polygon.getXYArray();
		{
			GAction00 action = new GAction00(this);
			action.setZ(getAirCutHeight());
			action.setX(array[0].getX());
			action.setY(array[0].getY());
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
			action.setX(array[i].getX());
			action.setY(array[i].getY());
			action.setZ(getCutHeight());
			action.setF(getCutSpeed());
			add(action);
		}
		{
			GAction00 action = new GAction00(this);
			action.setZ(getAirCutHeight());
			add(action);
		}
		return drawables.add(polygon);
	}
	
}

package st.tori.cnc.stencil.gerber.parser;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.DrawableCollection;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.applet.DimensionController;
import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.exception.ApertureNotDefinedException;
import st.tori.cnc.stencil.gerber.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gerber.exception.NoLastStatementExistsException;
import st.tori.cnc.stencil.gerber.exception.NotYetFormatSpecifiedException;
import st.tori.cnc.stencil.gerber.statement.StatementInterface;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberAperture;
import st.tori.cnc.stencil.gerber.statement.function.DStatement10orHigher;
import st.tori.cnc.stencil.gerber.statement.function.GStatement;
import st.tori.cnc.stencil.gerber.statement.function.GStatement02;
import st.tori.cnc.stencil.gerber.statement.function.GStatement03;
import st.tori.cnc.stencil.gerber.statement.function.GStatement36;
import st.tori.cnc.stencil.gerber.statement.function.GStatement37;
import st.tori.cnc.stencil.gerber.statement.function.GStatement74;
import st.tori.cnc.stencil.gerber.statement.function.GStatement75;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacro;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementAM;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementFS;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementIP;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementLP;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementMO;
import st.tori.cnc.stencil.gerber.statement.parameter.PStatementSR;
import st.tori.cnc.stencil.util.NumberUtil;


public class Gerber extends ArrayList<StatementInterface> implements Drawable {

	private static final String RET = "\n";

	private boolean initialized = false;
	private boolean finalized = false;
	
	public final void initialize() {
		initialized = true;
	}
	public final void finalize() {
		if(!initialized)return;
		finalized = true;
	}
	
	private Color color = null;
	public void setColor(Color color) {	this.color = color;	}
	public Color getColor(){	return color;	}
		
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator<StatementInterface> ite = super.iterator();
		StatementInterface lastStatement = null;
		GStatement lastGStatement = null;
		PositionXYInterface lastPosition = null;
		while(ite.hasNext()) {
			StatementInterface statement = ite.next();
			if(lastStatement!=null)
				buf.append(RET);
			buf.append(statement.getSimpleName());
			if(statement instanceof PositionXYInterface) {
				PositionXYInterface position = (PositionXYInterface)statement;
				if(lastPosition==null||lastPosition.getX()!=position.getX()||lastPosition.getY()!=position.getY()) {
					buf.append("X").append(NumberUtil.toGCodeValue(position.getX()));
					buf.append("Y").append(NumberUtil.toGCodeValue(position.getY()));
				}
				lastPosition = position;
			}
			lastStatement = statement;
		}
		buf.append(RET);
		//Remove ret+ret which inserts around first Z-XY positioning
		return buf.toString().replaceAll(RET+RET+"?", RET);
	}
	
	public enum ZERO_OMISSION_MODE {
		OMIT_LEADING_ZEROS,
		OMIT_TRAILING_ZEROS,
	}
	public enum UNIT_MODE {
		INCHES,
		MILLIMETERS,
	}
	public enum IMAGE_POLARITY {
		POSITIVE,
		NEGATIVE,
	}
	public enum COORDINATE_VALUES_NOTATION {
		ABSOLUTE_NOTATION,
		INCREMENTAL_NOTATION,
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
	
	private ZERO_OMISSION_MODE zeroOmissionMode = null;
	private COORDINATE_VALUES_NOTATION coordinateValuesNotation;
	private int XN;
	private int XM;
	private int YN;
	private int YM;
	
	public double parseX(String strValue) throws NotYetFormatSpecifiedException {
		return parse(strValue, XN, XM);
	}
	public double parseY(String strValue) throws NotYetFormatSpecifiedException {
		return parse(strValue, YN, YM);
	}
	private double parse(String strValue, int n, int m) throws NotYetFormatSpecifiedException {
		if(zeroOmissionMode==null) {
			throw new NotYetFormatSpecifiedException();
		}else if(zeroOmissionMode==ZERO_OMISSION_MODE.OMIT_LEADING_ZEROS) {
			while(strValue.length()<n+m)
				strValue = "0"+strValue;
		}else if(zeroOmissionMode==ZERO_OMISSION_MODE.OMIT_TRAILING_ZEROS) {
			while(strValue.length()<n+m)
				strValue += "0";
		}
		return Double.parseDouble(strValue.substring(0,n))+Double.parseDouble("0."+strValue.substring(n));
	}

	private UNIT_MODE unitMode = null;
	private IMAGE_POLARITY imagePolarity = null;
	
	private List<GerberLevel> levels = new ArrayList<GerberLevel>();
	
	protected GerberLevel getCurrentLevel() {
		return levels.get(levels.size()-1);
	}
	
	private int xRepeats;
	private int yRepeats;
	private double xStep;
	private double yStep;
	
	private INTERPOLATION_MODE interpolation = INTERPOLATION_MODE.UNDEF;
	private REGION_MODE region = REGION_MODE.UNDEF;
	private QUADRANT_MODE quadrant = QUADRANT_MODE.UNDEF;
	
	private GStatement lastStatement = null;
	private PositionXYInterface lastPosition = null;
	
	public GStatement getLastStatement(){	return lastStatement;	}
	public PositionXYInterface getLastPosition(){	return lastPosition;	}
	
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
	
	@Override
	public final boolean add(StatementInterface statement) {
		if(statement instanceof PStatementFS) {
			PStatementFS _statement = (PStatementFS)statement;
			zeroOmissionMode = _statement.zeroOmissionMode;
			coordinateValuesNotation = _statement.coordinateValuesNotation;
			XN = _statement.XN;
			XM = _statement.XM;
			YN = _statement.YN;
			YM = _statement.YM;
		}else if(statement instanceof PStatementMO) {
			unitMode = ((PStatementMO)statement).unitMode;
		}else if(statement instanceof PStatementIP) {
			imagePolarity = ((PStatementIP)statement).imagePolarity;
		}else if(statement instanceof PStatementLP) {
			levels.add(new GerberLevel(((PStatementLP)statement).polarity));
		}else if(statement instanceof PStatementSR) {
			PStatementSR _statement = (PStatementSR)statement;
			xRepeats = _statement.xRepeats;
			yRepeats = _statement.yRepeats;
			xStep = _statement.xStep;
			yStep = _statement.yStep;
		}else if(statement instanceof PStatementAM) {
			PStatementAM _statement = (PStatementAM)statement;
			setMacros(_statement.getName(), _statement.getMacros());
		}else if(statement instanceof GerberAperture) {
			GerberAperture _statement = (GerberAperture)statement;
			setAperture(_statement.getDcode(), _statement);
		}else if(statement instanceof GStatement02) {
			interpolation = INTERPOLATION_MODE.CLOCKWISE_CIRCULAR;
		}else if(statement instanceof GStatement03) {
			interpolation = INTERPOLATION_MODE.COUNTERCLOCKWISE_CIRCULAR;
		}else if(statement instanceof GStatement36) {
			region = REGION_MODE.ON;
		}else if(statement instanceof GStatement37) {
			region = REGION_MODE.OFF;
		}else if(statement instanceof GStatement74) {
			quadrant = QUADRANT_MODE.SINGLE;
		}else if(statement instanceof GStatement75) {
			quadrant = QUADRANT_MODE.MULTI;
		}else if(statement instanceof DStatement10orHigher) {
			setCurrentAperture(((DStatement10orHigher)statement).getAperture());
		}
		if(statement instanceof GStatement)
			lastStatement = (GStatement)statement;
		if(statement instanceof PositionXYInterface)
			lastPosition = (PositionXYInterface)statement;
		if(statement instanceof DrawableCollection) {
			Collection<Drawable> collection = ((DrawableCollection)statement).getDrawables();
			System.out.print("Adding "+collection.size()+" drawables:");
			Iterator<Drawable> ite = collection.iterator();
			while(ite.hasNext())
				System.out.print(ite.next());
			System.out.println();
			drawables.addAll(collection);
		}
		if(statement instanceof Drawable) {
			drawables.add((Drawable)statement);
			System.out.println("Adding drawable:"+statement);
		}
		return super.add(statement);
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
	
	private Map<Integer, GerberAperture> APERTURE_MAP = new HashMap<Integer, GerberAperture>();
	
	private void setAperture(int dcode, GerberAperture aperture) {
		APERTURE_MAP.put(dcode, aperture);
	}
	public GerberAperture getAperture(int dcode) throws ApertureNotDefinedException {
		GerberAperture aperture = APERTURE_MAP.get(dcode);
		if(aperture==null)
			throw new ApertureNotDefinedException(dcode);
		return aperture;
	}
	
	private GerberAperture currentAperture = null;
	
	public void setCurrentAperture(GerberAperture aperture) {
		this.currentAperture = aperture;
	}
	public GerberAperture getCurrentAperture() {
		return currentAperture;
	}
	
	private Map<String, List<GerberMacro>> MACROS_MAP = new HashMap<String, List<GerberMacro>>();
	
	private void setMacros(String name, List<GerberMacro> macros) {
		MACROS_MAP.put(name, macros);
	}
	public List<GerberMacro> getMacros(String name) throws ApertureMacroNotDefinedException {
		List<GerberMacro> macros = MACROS_MAP.get(name);
		if(macros==null)
			throw new ApertureMacroNotDefinedException(name);
		return macros;
	}

}

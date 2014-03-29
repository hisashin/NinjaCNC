package st.tori.cnc.stencil.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.PositionXYZInterface;
import st.tori.cnc.stencil.gcode.action.ActionInterface;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.exception.CannotDetectCornerHolesException;
import st.tori.cnc.stencil.gcode.exception.NotCornerDrillingException;
import st.tori.cnc.stencil.gcode.exception.NotLayerHoleDrillingException;
import st.tori.cnc.stencil.gcode.exception.NotSimpleHoleDrillingException;
import st.tori.cnc.stencil.gcode.exception.TooDeepCutHeightException;
import st.tori.cnc.stencil.gcode.parser.GCode;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GCodeUtil {

	/*
	 * Create GCode from Gerber
	 */
	public static GCode createGCodeForSolderStencil(Gerber creamGerber, Drill drill) {
		//TODO
		return null;
	}

	/*
	 * Detect 4 corner holes and remove them
	 */
	public static GCode remove4CornerHoles(GCode gCode) throws CannotDetectCornerHolesException {
		Deepest4Corners corners = new Deepest4Corners(gCode);
		GCode newCode = new GCode(gCode.getDrill());
		Iterator<ActionInterface> ite = gCode.iterator();
		while(ite.hasNext()) {
			ActionInterface action = ite.next();
			if(action instanceof PositionXYZInterface) {
				PositionXYZInterface position = (PositionXYZInterface)action;
				if(!PositionUtil.isDistantXYZ(position, corners.deepest_minXminY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_maxXminY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_maxXmaxY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_minXmaxY)) {
					//skip adding
				}else{
					newCode.add(action);
				}
			}else{
				newCode.add(action);
			}
		}
		return newCode;
	}
	/*
	 * Detect 4 corner holes and deepen it to increase proofreading precision
	 */
	public static GCode deepDrilling4CornerHoles(GCode gCode, double deepCutHeight) throws CannotDetectCornerHolesException, TooDeepCutHeightException {
		if(gCode.getDrill().getMaxZInMm()<deepCutHeight)
			throw new TooDeepCutHeightException(gCode.getDrill().getMaxZInMm(),deepCutHeight);
		Deepest4Corners corners = new Deepest4Corners(gCode);
		GCode newCode = new GCode(gCode.getDrill());
		Iterator<ActionInterface> ite = gCode.iterator();
		while(ite.hasNext()) {
			ActionInterface action = ite.next();
			if(action instanceof PositionXYZInterface) {
				PositionXYZInterface position = (PositionXYZInterface)action;
				if(!PositionUtil.isDistantXYZ(position, corners.deepest_minXminY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_maxXminY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_maxXmaxY)
					||!PositionUtil.isDistantXYZ(position, corners.deepest_minXmaxY)) {
					position.setZ(deepCutHeight);
					newCode.add((ActionInterface)position);
				}else{
					newCode.add(action);
				}
			}else{
				newCode.add(action);
			}
		}
		return newCode;
	}
	private static class Deepest4Corners {
		
		private CornerDrillingOperator corner_minXminY;
		private CornerDrillingOperator corner_maxXminY;
		private CornerDrillingOperator corner_maxXmaxY;
		private CornerDrillingOperator corner_minXmaxY;
		
		public Deepest4Corners(GCode gCode) throws CannotDetectCornerHolesException {
			PositionXYInterface minXminY = null;
			PositionXYInterface maxXmaxY = null;
			PositionXYInterface minXmaxY = null;
			PositionXYInterface maxXminY = null;
			Iterator<ActionInterface> ite;
			ite = gCode.iterator();
			while(ite.hasNext()) {
				ActionInterface action = ite.next();
				if(!(action instanceof PositionXYZInterface))continue;
				PositionXYZInterface position = (PositionXYZInterface)action;
				if(position.getZ()>0)continue;
				if(minXminY==null||+minXminY.getX()+minXminY.getY()>+position.getX()+position.getY())
					minXminY = position;
				if(maxXmaxY==null||+maxXmaxY.getX()+maxXmaxY.getY()<+position.getX()+position.getY())
					maxXmaxY = position;
				if(minXmaxY==null||-minXmaxY.getX()+minXmaxY.getY()>-position.getX()+position.getY())
					minXmaxY = position;
				if(maxXminY==null||+maxXminY.getX()-maxXminY.getY()>+position.getX()-position.getY())
					maxXminY = position;
			}
			System.out.println("minXminY=("+minXminY.getX()+","+minXminY.getY()+")");
			System.out.println("maxXminY=("+maxXminY.getX()+","+maxXminY.getY()+")");
			System.out.println("maxXmaxY=("+maxXmaxY.getX()+","+maxXmaxY.getY()+")");
			System.out.println("minXmaxY=("+minXmaxY.getX()+","+minXmaxY.getY()+")");
			
			if(!PositionUtil.isDistantXY(minXminY, maxXminY)
				||!PositionUtil.isDistantXY(maxXminY, maxXmaxY)
				||!PositionUtil.isDistantXY(maxXmaxY, minXmaxY)
				||!PositionUtil.isDistantXY(minXmaxY, minXminY))
				throw new CannotDetectCornerHolesException("Couldn't find distinct 4 holes");

			try {
				this.corner_minXminY = getCornerDrillingOperator(gCode, minXminY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner minXminY is not corner drilling:"+e.getMessage());
			}
			try {
				this.corner_maxXminY = getCornerDrillingOperator(gCode, maxXminY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner maxXminY is not corner drilling:"+e.getMessage());
			}
			try {
				this.corner_maxXmaxY = getCornerDrillingOperator(gCode, maxXmaxY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner maxXmaxY is not corner drilling:"+e.getMessage());
			}
			try {
				this.corner_minXmaxY = getCornerDrillingOperator(gCode, minXmaxY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner minXmaxY is not corner drilling:"+e.getMessage());
			}
		}
	}
	private static abstract class CornerDrillingOperator {
		protected List<ActionInterface> relatedCut;
		public CornerDrillingOperator(List<ActionInterface> relatedCut) {
			this.relatedCut = relatedCut;
		}
	}
	private static class SimpleDrilling extends CornerDrillingOperator {
		
		private PositionXYZInterface deepestPoint;
		
		public SimpleDrilling(List<ActionInterface> relatedCut) throws NotSimpleHoleDrillingException {
			super(relatedCut);
			if(relatedCut==null)throw new NotSimpleHoleDrillingException("Related cut contains no process");
			if(relatedCut.size()!=3)
				throw new NotSimpleHoleDrillingException("Related cut is not 3 stepped but "+relatedCut.size());
			PositionXYZInterface p0 = (PositionXYZInterface)relatedCut.get(0);
			PositionXYZInterface p1 = (PositionXYZInterface)relatedCut.get(1);
			PositionXYZInterface p2 = (PositionXYZInterface)relatedCut.get(2);
			if(p0==null||p1==null||p2==null)throw new NotSimpleHoleDrillingException("Position is ambiguous");
			if(PositionUtil.isDistantXY(p0, p1)||PositionUtil.isDistantXY(p1, p2))throw new NotSimpleHoleDrillingException("Related cuts moves");
			if(p0.getZ()<=0||p1.getZ()>0||p2.getZ()<=0)throw new NotSimpleHoleDrillingException("Related cut is not simple drill-down-up");
			if(p0.getZ()!=p2.getZ())throw new NotSimpleHoleDrillingException("Related cut changes its height in process");
			this.deepestPoint = p1;
		}
		
	}
	private static class LayerDrilling extends CornerDrillingOperator {
		
		private Map<Double, List<String>> MAP_DEPTH_BASED = new HashMap<Double, List<String>>();
		private Map<String, List<Double>> MAP_XY_BASED = new HashMap<String, List<Double>>();
		
		public LayerDrilling(List<ActionInterface> relatedCut) throws NotLayerHoleDrillingException {
			super(relatedCut);
			if(relatedCut==null)throw new NotLayerHoleDrillingException("Related cut contains no process");
			if(relatedCut.size()<3)
				throw new NotLayerHoleDrillingException("Related cut is less than 3 stepped but "+relatedCut.size());
			PositionXYZInterface pFirst = (PositionXYZInterface)relatedCut.get(0);
			PositionXYZInterface pLast = (PositionXYZInterface)relatedCut.get(relatedCut.size()-1);
			if(pFirst.getZ()<=0||pLast.getZ()<=0)
				throw new NotLayerHoleDrillingException("First and last point is not aircutting");
			if(pFirst.getZ()!=pLast.getZ())
				throw new NotLayerHoleDrillingException("Related cut changes its height in process");
			PositionXYZInterface lastPoint = pFirst;
			String key;
			for(int i=1;i<relatedCut.size()-1;i++) {
				PositionXYZInterface p = (PositionXYZInterface)relatedCut.get(i);
				if(p.getZ()>0)
					throw new NotLayerHoleDrillingException(i+"th point is aircutting");
				if(p.getZ()>lastPoint.getZ())
					throw new NotLayerHoleDrillingException(i+"th point ("+p.getX()+","+p.getY()+","+p.getZ()+") is over than last ("+lastPoint.getX()+","+lastPoint.getY()+","+lastPoint.getZ()+")");
				{
					List<String> subList = MAP_DEPTH_BASED.get(p.getZ());
					if(subList==null) {
						subList = new ArrayList<String>();
						MAP_DEPTH_BASED.put(p.getZ(), subList);
					}
					key = getXYKey(p);
					if(!subList.contains(key))
						subList.add(key);
				}
				{
					key = getXYKey(p);
					List<Double> subList = MAP_XY_BASED.get(key);
					if(subList==null) {
						subList = new ArrayList<Double>();
						MAP_XY_BASED.put(key, subList);
					}
					if(!subList.contains(p.getZ()))
						subList.add(p.getZ());
				}
				checkMapDepthBased();
				checkMapXYBased();
			}
		}
		private void checkMapDepthBased() throws NotLayerHoleDrillingException {
			if(MAP_DEPTH_BASED.size()<=0)
				throw new NotLayerHoleDrillingException("No Depth data");
			Iterator<Double> ite = MAP_DEPTH_BASED.keySet().iterator();
			while(ite.hasNext()) {
				Double depth = ite.next();
				List<String> list = MAP_DEPTH_BASED.get(depth);
				if(list==null||list.size()<=0)
					throw new NotLayerHoleDrillingException("No Depth data");
			}
		}
		private void checkMapXYBased() throws NotLayerHoleDrillingException {
			
		}
		private static String getXYKey(PositionXYInterface p) {
			return p.getX()+"_"+p.getY();
		}
		
	}
	private static CornerDrillingOperator getCornerDrillingOperator(GCode gCode, PositionXYInterface position) throws NotCornerDrillingException {
		List<List<ActionInterface>> relatedCuts = getRelatedCuts(gCode, position);
		if(relatedCuts==null)throw new NotCornerDrillingException("No related cut found");
		if(relatedCuts.size()!=1)throw new NotCornerDrillingException("More than 1 related cuts found");
		List<ActionInterface> relatedCut = relatedCuts.get(0);
		try {
			return new SimpleDrilling(relatedCut);
		} catch (NotSimpleHoleDrillingException e1) {
			try {
				return new LayerDrilling(relatedCut);
			} catch (NotLayerHoleDrillingException e2) {
				throw new NotCornerDrillingException(e2.getMessage());
			}
		}
	}
	private static List<List<ActionInterface>> getRelatedCuts(GCode gCode, PositionXYInterface target) {
		List<List<ActionInterface>> list = new ArrayList<List<ActionInterface>>();
		Iterator<ActionInterface> ite = gCode.iterator();
		PositionXYZInterface lastPosition = null;
		List<ActionInterface> subList = null;
		boolean related = false;
		while(ite.hasNext()) {
			ActionInterface action = ite.next();
			if(!(action instanceof PositionXYZInterface))continue;
			PositionXYZInterface position = (PositionXYZInterface)action;
			if(lastPosition==null) {
				lastPosition = position;
			}else if(position.getZ()>0){
				if(lastPosition.getZ()<=0) {
					//End cutting.
					if(related) {
						subList.add(action);
						list.add(subList);
					}
					subList = null;
					related = false;
				}else{
					//AirCutting
				}
				lastPosition = position;
			}else if(lastPosition.getZ()>0){
				//Start cutting.Start saving actions to subList
				subList = new ArrayList<ActionInterface>();
				subList.add((ActionInterface)lastPosition);
				subList.add(action);
				related = (!PositionUtil.isDistantXY(target,position));
				lastPosition = position;
			}else if(subList!=null){
				//Cutting
				subList.add(action);
				if(!PositionUtil.isDistantXY(target,position))
					related = true;
			}
			
		}
		return list;
	}
	private static void printRelatedCuts(List<List<ActionInterface>> relatedCuts, String prefix) {
		System.out.println(prefix+":relatedCuts.size()="+relatedCuts.size());
		for(int i=0;i<relatedCuts.size();i++) {
			System.out.println(prefix+":relatedCuts["+i+"].size()="+relatedCuts.get(i).size());
			Iterator<ActionInterface> iteR = relatedCuts.get(i).iterator();
			while(iteR.hasNext()) {
				PositionXYZInterface position = (PositionXYZInterface)iteR.next();
				System.out.println("("+position.getX()+","+position.getY()+","+position.getZ()+")");
			}
		}
	}
	
	/*
	 * Change drill setting only for outline so that thick cheap drill can be used to cut.
	 * Maybe ignore large drill holes.
	 */
	public static GCode changeDrillForOutline(GCode gCode) {
		//TODO
		return gCode;
	}
	
}

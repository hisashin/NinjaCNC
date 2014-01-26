package st.tori.cnc.stencil.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		
		private List<ActionInterface> actions_minXminY;
		private List<ActionInterface> actions_maxXminY;
		private List<ActionInterface> actions_maxXmaxY;
		private List<ActionInterface> actions_minXmaxY;
		
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
				this.actions_minXminY = getCornerDrillingActions(gCode, minXminY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner minXminY is not corner drilling:"+e.getMessage());
			}
			try {
				this.actions_maxXminY = getCornerDrillingActions(gCode, maxXminY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner maxXminY is not corner drilling:"+e.getMessage());
			}
			try {
				this.actions_maxXmaxY = getCornerDrillingActions(gCode, maxXmaxY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner maxXmaxY is not corner drilling:"+e.getMessage());
			}
			try {
				this.actions_minXmaxY = getCornerDrillingActions(gCode, minXmaxY);
			} catch (NotCornerDrillingException e) {
				throw new CannotDetectCornerHolesException("Corner minXmaxY is not corner drilling:"+e.getMessage());
			}
		}
	}
	private static List<ActionInterface> getCornerDrillingActions(GCode gCode, PositionXYInterface position) throws NotCornerDrillingException {
		List<List<ActionInterface>> relatedCuts = getRelatedCuts(gCode, position);
		if(relatedCuts==null)throw new NotCornerDrillingException("No related cut found");
		if(relatedCuts.size()!=1)throw new NotCornerDrillingException("More than 1 related cuts found");
		try {
			if(isSimpleHoleDrilling(relatedCuts))
				return relatedCuts.get(0);
		} catch (NotSimpleHoleDrillingException e) {
			try {
				if(isLayerHoleDrilling(relatedCuts))
					return relatedCuts.get(0);
			} catch (NotLayerHoleDrillingException e) {
				throw new NotCornerDrillingException(e.getMessage());
			}
		}
	}
	private static boolean isSimpleHoleDrilling(List<List<ActionInterface>> relatedCuts) throws NotSimpleHoleDrillingException {
		List<ActionInterface> subList = relatedCuts.get(0);
		if(subList==null)throw new NotSimpleHoleDrillingException("Related cuts contains no process");
		if(subList.size()!=3){
			printRelatedCuts(relatedCuts, "NotSimpleHoleDrillingException");
			throw new NotSimpleHoleDrillingException("Related cuts is not 3 stepped but "+subList.size());
		}
		PositionXYZInterface p0 = (PositionXYZInterface)subList.get(0);
		PositionXYZInterface p1 = (PositionXYZInterface)subList.get(1);
		PositionXYZInterface p2 = (PositionXYZInterface)subList.get(2);
		if(p0==null||p1==null||p2==null)throw new NotSimpleHoleDrillingException("Position is ambiguous");
		if(PositionUtil.isDistantXY(p0, p1)||PositionUtil.isDistantXY(p1, p2))throw new NotSimpleHoleDrillingException("Related cuts moves");
		if(p0.getZ()<=0||p1.getZ()>0||p2.getZ()<=0)throw new NotSimpleHoleDrillingException("Related cuts are not simple drill-down-up");
		if(p0.getZ()!=p2.getZ())throw new NotSimpleHoleDrillingException("Related cuts changes its height in process");
		return true;
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

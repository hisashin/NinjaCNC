package st.tori.cnc.stencil.dxf.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.SimpleXY;
import st.tori.cnc.stencil.canvas.shape.Polygon;
import st.tori.cnc.stencil.dxf.exception.DxfException;
import st.tori.cnc.stencil.dxf.exception.OffsetNotSupportedException;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.exception.IllegalReflectionException;
import st.tori.cnc.stencil.gcode.exception.NoLastActionExistsException;
import st.tori.cnc.stencil.gcode.exception.PositionNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.SpeedNotSupportedException;
import st.tori.cnc.stencil.gcode.exception.UnsupportedIndexException;
import st.tori.cnc.stencil.gcode.exception.UnsupportedPrefixException;
import st.tori.cnc.stencil.util.FileUtil;
import st.tori.cnc.stencil.util.PositionUtil;

public class DxfParser {

	public Dxf parse(Drill drill, File file) throws UnsupportedPrefixException, UnsupportedIndexException, PositionNotSupportedException, SpeedNotSupportedException, NoLastActionExistsException, IllegalReflectionException, IllegalLineException, OffsetNotSupportedException {
		Dxf dxf = new Dxf(drill);
		dxf.initialize(5, 1, -0.15, 100.0, 300.0);
		List<String> list = FileUtil.readFileAsStringList(file);
		LineIterator ite = new LineIterator(list.iterator());
		while(ite.hasNext()) {
			Line line = ite.next();
			System.out.println("num="+line.num+"\tval="+line.val);
			if(line.num==0&&"SECTION".equals(line.val)) {
				parseHeader(dxf, ite);
			}else if(line.num==0&&"LWPOLYLINE".equals(line.val)) {
				parsePolyline(dxf, ite);
			}else if(line.num==0&&"LINE".equals(line.val)) {
				parseLine(dxf, ite);
			}else if(line.num==0&&"ARC".equals(line.val)) {
				parseArc(dxf, ite);
			}else if(line.num==0&&"CIRCLE".equals(line.val)) {
				parseCircle(dxf, ite);
			}else if(line.num==0&&"ENDSEC".equals(line.val)) {
				parseTrailer(dxf, ite);
			}else{
				throw new IllegalLineException(ite.getLineIndex(), "num="+line.num+",val="+line.val);
			}
		}
		dxf.finalize();
		System.out.println("There are "+list.size()+" lines,"+dxf.size()+" actions");
		return dxf;
	}

	private void parseHeader(Dxf dxf, LineIterator ite) {
		Line line;
		while(ite.hasNext()) {
			line = ite.next();
			System.out.println("Header:"+line.val+"->"+line.num);
			if("ENTITIES".equals(line.val))
				return;
		}
	}

	private void parsePolyline(Dxf dxf, LineIterator ite) throws IllegalLineException, OffsetNotSupportedException {
		Line line;
		int numberOfPoints = 0;
		int parsedPoints = 0;
		boolean closed = false;
		PositionXYInterface lastPosition = null;
		List<PositionXYInterface> xyList = new ArrayList<PositionXYInterface>();
		while(ite.hasNext()) {
			line = ite.next();
			//System.out.println("Polyline:"+line.val+"->"+line.num);
			if(line.num==8)
				System.out.println("Polyline:set layer to "+line.val);
			else if(line.num==62)
				System.out.println("Polyline:set color to "+line.val);
			else if(line.num==90) {
				System.out.println("Polyline:set numberOfPoints to "+line.val);
				numberOfPoints = Integer.parseInt(line.val);
			}else if(line.num==70) {
				System.out.println("Polyline:set close to "+line.val+"(0=open,1=close)");
				closed = "1".equals(line.val);
			}else if(line.num==10) {
				double x = Double.parseDouble(line.val);
				if(!ite.hasNext())
					throw new IllegalLineException(ite.getLineIndex(), "No line for y");
				double y = Double.parseDouble(ite.next().val);
				//System.out.println("Polyline:set (x,y)=("+x+","+y+")");
				PositionXYInterface position = new SimpleXY(x, y);
				if(lastPosition!=null&&!PositionUtil.isDistantXY(lastPosition, position)) {
					parsedPoints++;
				}else if(PositionUtil.contains(xyList, position)) {
					xyList.add(position);
					//stop adding overcuts
					break;
				}else{
					xyList.add(position);
					parsedPoints++;
				}
				lastPosition = position;
				//System.out.println("Polyline:numberOfPoints="+numberOfPoints+",parsedPoints="+parsedPoints);
			}
			if(numberOfPoints>0&&numberOfPoints==parsedPoints){
				if(closed&&PositionUtil.isDistantXY(xyList.get(0), xyList.get(xyList.size()-1)))
					xyList.add(xyList.get(0));
				break;
			}
		}
		float stroke = dxf.getStroke();
		if(xyList.size()>3) {
		//if(xyList.size()>0&&!PositionUtil.isSameXY(xyList)) {
			dxf.add(new Polygon(getStrokeOffset(xyList, stroke), stroke));
		}
	}
	private static PositionXYInterface[] getStrokeOffset(List<PositionXYInterface> xyList, float stroke) throws OffsetNotSupportedException {
		if(xyList==null||PositionUtil.isDistantXY(xyList.get(0), xyList.get(xyList.size()-1)))throw new OffsetNotSupportedException(xyList);
		System.out.println(" xyList:"+OffsetNotSupportedException.toString(xyList));
		List<PositionXYInterface> _xyList = new ArrayList<PositionXYInterface>();
		int lastIndex = xyList.size()-1;
		PositionXYInterface init = xyList.get(0);
		PositionXYInterface last = xyList.get(lastIndex);
		while(!PositionUtil.isDistantXY(last, init)) {
			lastIndex--;
			last = xyList.get(lastIndex);
		}
		int nextIndex;
		PositionXYInterface current,next,lastVec,nextVec,mergedVec;
		double rad,length,_x,_y;
		for(int i=0;i<xyList.size();i++) {
			current = xyList.get(i);
			nextIndex = i+1;
			if(nextIndex>xyList.size()-1)nextIndex = 0;
			next = xyList.get(nextIndex);
			while(!PositionUtil.isDistantXY(current, next)) {
				nextIndex++;
				if(nextIndex>xyList.size()-1)nextIndex = 0;
				next = xyList.get(nextIndex);
			}
			lastVec = PositionUtil.getDiffVector(current, last, true);
			nextVec = PositionUtil.getDiffVector(current, next, true);
			mergedVec = PositionUtil.getMergedVector(lastVec, nextVec, true);
			if(PositionUtil.getVectorLength(mergedVec)==0.0){
				//skip this point.keep last as last and need not replace current to last.
				continue;
			}
			rad = PositionUtil.getNarrowAngleBetween(lastVec, nextVec);
			length = stroke/(2*Math.sin(rad/2.0));
			_x = current.getX()+mergedVec.getX()*length;
			_y = current.getY()+mergedVec.getY()*length;
			//System.out.println("(x,y)=("+current.getX()+","+current.getY()+")-->(x,y)=("+_x+","+_y+") length="+length+",deg="+DegUtil.toDeg(rad)+",Math.sin(rad/2.0)="+Math.sin(rad/2.0)+" mergedVec=("+mergedVec.getX()+","+mergedVec.getY()+")");
			_xyList.add(new SimpleXY(_x,_y));
			last = current;
		}
		System.out.println("_xyList:"+OffsetNotSupportedException.toString(_xyList));
		return _xyList.toArray(new PositionXYInterface[_xyList.size()]);
	}

	private void parseLine(Dxf dxf, LineIterator ite) {
		// TODO Auto-generated method stub
		
	}

	private void parseArc(Dxf dxf, LineIterator ite) {
		// TODO Auto-generated method stub
		
	}

	private void parseCircle(Dxf dxf, LineIterator ite) {
		// TODO Auto-generated method stub
		
	}

	private void parseTrailer(Dxf dxf, LineIterator ite) {
		Line line;
		while(ite.hasNext()) {
			line = ite.next();
			System.out.println("Trailer:"+line.val+"->"+line.num);
			if("EOF".equals(line.val))
				return;
		}
	}

	private static class Line {
		private int num;
		private String val;
		public Line(int num, String val) {
			this.num = num;
			this.val = val;
		}
	}
	private static class LineIterator implements Iterator<Line> {
		
		private Iterator<String> ite;
		private int lineIndex;
		
		public LineIterator(Iterator<String> ite) {
			this.ite = ite;
			this.lineIndex = 1;
		}
		
		private Line nextLine;
		
		@Override
		public boolean hasNext() {
			nextLine = null;
			int num;
			String line,val;
			try {
				if(!ite.hasNext())
					throw new NoMoreLineException();
				line = ite.next();
				try {
					num = Integer.parseInt(line.trim());
				}catch(Exception e){
					throw new IllegalLineException(lineIndex, line);
				}
				lineIndex++;
				if(!ite.hasNext())
					throw new IllegalLineException(lineIndex, "No even line");
				try {
					val = ite.next().trim();
				}catch(Exception e){
					throw new IllegalLineException(lineIndex, line);
				}
				lineIndex++;
				nextLine = new Line(num,val);
			}catch(IllegalLineException e){
				e.printStackTrace();
				return false;
			}catch(NoMoreLineException e){
				return false;
			}
			return (nextLine!=null);
		}
		@Override
		public Line next() {
			return nextLine;
		}
		@Override
		public void remove() {
			//nothing happen
		}
		public int getLineIndex() {
			return lineIndex;
		}
	}
	private static class NoMoreLineException extends DxfException {
		public NoMoreLineException() {
			super("No more line");
		}
	}
	private static class IllegalLineException extends DxfException {
		private int lineIndex;
		private String line;
		public IllegalLineException(int lineIndex, String line) {
			super("Illegal line format on "+lineIndex+":"+line);
			this.lineIndex = lineIndex;
			this.line = line;
		}
	}
	
}

package st.tori.cnc.stencil.gerber.statement.function;

import java.util.ArrayList;
import java.util.Collection;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.DrawableCollection;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.shape.Line;
import st.tori.cnc.stencil.gerber.parser.Gerber;

/*
 * Flash operation code
 */
public class DStatement03 extends DStatement implements DrawableCollection {

	@Override
	protected int getDIndex() {	return 3;	}

	private Collection<Drawable> collection = new ArrayList<Drawable>();
	
	public DStatement03(PositionXYInterface position, Gerber gerber) {
		super(position, gerber);
		PositionXYInterface lastPosition = gerber.getLastPosition();
		collection.add(new Line(lastPosition, position, gerber.getCurrentAperture().getStroke(lastPosition,position)));
	}

	@Override
	public Collection<Drawable> getDrawables() {	return collection;	}

}

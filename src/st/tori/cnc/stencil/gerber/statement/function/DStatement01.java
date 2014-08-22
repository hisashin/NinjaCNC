package st.tori.cnc.stencil.gerber.statement.function;

import java.util.ArrayList;
import java.util.Collection;

import st.tori.cnc.stencil.canvas.Drawable;
import st.tori.cnc.stencil.canvas.DrawableCollection;
import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.canvas.shape.Line;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberAperture;

/*
 * Interpolate operation code
 */
public class DStatement01 extends DStatement implements DrawableCollection {

	@Override
	protected int getDIndex() {	return 1;	}

	private Collection<Drawable> collection = new ArrayList<Drawable>();
	
	public DStatement01(PositionXYInterface position, Gerber gerber) {
		super(position, gerber);
		PositionXYInterface lastPosition = gerber.getLastPosition();
		GerberAperture aperture = gerber.getCurrentAperture();
		Collection<Drawable> sub;
		sub = aperture.createDrawableCollection(lastPosition);
		if(sub!=null)
			collection.addAll(sub);
		collection.add(new Line(lastPosition, position, aperture.getStroke(lastPosition,position)));
		sub = aperture.createDrawableCollection(position);
		if(sub!=null)
			collection.addAll(sub);
	}

	@Override
	public Collection<Drawable> getDrawables() {	return collection;	}
	
}

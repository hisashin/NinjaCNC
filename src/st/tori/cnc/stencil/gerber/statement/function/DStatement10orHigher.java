package st.tori.cnc.stencil.gerber.statement.function;

import st.tori.cnc.stencil.gerber.exception.ApertureNotDefinedException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.aperture.GerberAperture;

/*
 * Set the current aperture
 */
public class DStatement10orHigher extends DStatement {

	protected int dcode;
	protected GerberAperture aperture;
	
	@Override
	protected int getDIndex() {	return dcode;	}

	public DStatement10orHigher(int dcode, Gerber gerber) throws ApertureNotDefinedException {
		super(null, gerber);
		this.dcode = dcode;
		this.aperture = gerber.getAperture(dcode);
	}

	public GerberAperture getAperture() {
		return null;
	}

}

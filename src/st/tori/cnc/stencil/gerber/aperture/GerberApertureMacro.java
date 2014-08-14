package st.tori.cnc.stencil.gerber.aperture;

import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.parser.Gerber;


public class GerberApertureMacro extends GerberAperture {

	private String name;
	
	public GerberApertureMacro(int dcode, String name, Gerber gerber) throws ApertureMacroNotDefinedException {
		super(dcode, null, gerber);
		this.name = name;
		if(gerber.getMacro(name)==null)
			throw new ApertureMacroNotDefinedException(dcode, name);
	}
	
	public String getName(){	return name;	}

	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+getName();
	}


}

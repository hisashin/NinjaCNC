package st.tori.cnc.stencil.gerber.statement.aperture;

import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacro;


public class GerberApertureMacro extends GerberAperture {

	private String name;
	private GerberMacro macro;
	
	public GerberApertureMacro(int dcode, String name, Gerber gerber) throws ApertureMacroNotDefinedException {
		super(dcode, null, gerber);
		this.name = name;
		this.macro = gerber.getMacro(name);
			
	}
	
	public String getName(){	return name;	}
	public GerberMacro getMacro(){	return macro;	}

	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+getName();
	}


}

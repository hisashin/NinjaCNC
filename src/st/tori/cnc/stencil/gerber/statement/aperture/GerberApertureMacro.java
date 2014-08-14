package st.tori.cnc.stencil.gerber.statement.aperture;

import java.util.List;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.exception.ApertureMacroNotDefinedException;
import st.tori.cnc.stencil.gerber.parser.Gerber;
import st.tori.cnc.stencil.gerber.statement.macro.GerberMacro;


public class GerberApertureMacro extends GerberAperture {

	private String name;
	private List<GerberMacro> macros;
	
	public GerberApertureMacro(int dcode, String name, Gerber gerber) throws ApertureMacroNotDefinedException {
		super(dcode, null, gerber);
		this.name = name;
		this.macros = gerber.getMacros(name);
			
	}
	
	public String getName(){	return name;	}
	public List<GerberMacro> getMacros(){	return macros;	}

	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+getName();
	}


	@Override
	public float getStroke(PositionXYInterface lastPosition, PositionXYInterface position) {
		//TODO
		return (float)0.0;
	}

}

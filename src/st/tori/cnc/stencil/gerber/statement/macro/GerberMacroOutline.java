package st.tori.cnc.stencil.gerber.statement.macro;

import java.util.List;

import st.tori.cnc.stencil.canvas.PositionXYInterface;
import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroOutline extends GerberMacro {

	protected List<PositionXYInterface> positions;
	
	public GerberMacroOutline(int exposure, List<PositionXYInterface> positions, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.positions = positions;
	}
	
	public List<PositionXYInterface> getPositions(){	return positions;	}

}

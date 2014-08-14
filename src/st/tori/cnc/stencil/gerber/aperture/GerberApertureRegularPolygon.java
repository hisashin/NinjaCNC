package st.tori.cnc.stencil.gerber.aperture;

import st.tori.cnc.stencil.gerber.aperture.modifier.ApertureModifier;
import st.tori.cnc.stencil.gerber.parser.Gerber;


public class GerberApertureRegularPolygon extends GerberAperture {

	private double outerDiameter;
	private int numberOfVertices;
	private double degreesOfRotation;
	
	public GerberApertureRegularPolygon(int dcode, double outerDiameter, int numberOfVertices, double degreesOfRotation, ApertureModifier modifier, Gerber gerber) {
		super(dcode, modifier, gerber);
		this.outerDiameter = outerDiameter;
		this.numberOfVertices = numberOfVertices;
		this.degreesOfRotation = degreesOfRotation;
	}
	
	public double getOuterDiameter(){	return outerDiameter;	}
	public int getNumberOfVertices(){	return numberOfVertices;	}
	public double getDegreesOfRotation(){	return degreesOfRotation;	}

	@Override
	public String getSimpleName() {
		return "%ADD"+dcode+",P";
	}


}

package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.parser.Gerber;

public class GerberMacroPolygon extends GerberMacro {

	protected int numberOfVertices;
	protected double x;
	protected double y;
	protected double diameterOfCircumscribedCircle;
	
	public GerberMacroPolygon(int exposure, int numberOfVertices, double x, double y, double diameterOfCircumscribedCircle, double rotationAngle, Gerber gerber) {
		super(exposure, rotationAngle, gerber);
		this.numberOfVertices = numberOfVertices;
		this.x = x;
		this.y = y;
		this.diameterOfCircumscribedCircle = diameterOfCircumscribedCircle;
	}
	
	public int getNumberOfVertices(){	return numberOfVertices;	}
	public double getX(){	return x;	}
	public double getY(){	return y;	}
	public double getDiameterOfCircumscribedCircle(){	return diameterOfCircumscribedCircle;	}
	
}

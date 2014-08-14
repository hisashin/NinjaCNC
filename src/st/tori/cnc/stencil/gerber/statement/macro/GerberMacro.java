package st.tori.cnc.stencil.gerber.statement.macro;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;


public abstract class GerberMacro {

	protected boolean exposure;
	protected ArithmeticExpressionDouble rotationAngle;
	
	public GerberMacro(int exposure, ArithmeticExpressionDouble rotationAngle) {
		this.exposure = (exposure>=1);
		this.rotationAngle = rotationAngle;
	}
	
	public boolean getExposure(){	return exposure;	}
	public ArithmeticExpressionDouble getRotationAngle(){	return rotationAngle;	}

}

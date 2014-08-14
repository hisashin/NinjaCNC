package st.tori.cnc.stencil.gerber.statement.macro;

import java.util.List;

import st.tori.cnc.stencil.gerber.statement.StatementFactory.ArithmeticExpressionDouble;

public class GerberMacroOutline extends GerberMacro {

	protected List<ArithmeticExpressionXY> positions;
	
	public GerberMacroOutline(int exposure, List<ArithmeticExpressionXY> positions, ArithmeticExpressionDouble rotationAngle) {
		super(exposure, rotationAngle);
		this.positions = positions;
	}
	
	public static class ArithmeticExpressionXY {
		ArithmeticExpressionDouble x;
		ArithmeticExpressionDouble y;
		public ArithmeticExpressionXY(ArithmeticExpressionDouble x, ArithmeticExpressionDouble y) {
			this.x = x;
			this.y = y;
		}
		public ArithmeticExpressionDouble getX() {
			return x;
		}
		public ArithmeticExpressionDouble getY() {
			return y;
		}
	}
	
	public List<ArithmeticExpressionXY> getPositions(){	return positions;	}

}

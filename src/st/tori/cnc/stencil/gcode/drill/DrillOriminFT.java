package st.tori.cnc.stencil.gcode.drill;

/*
 * http://www.originalmind.co.jp/goods/07963
 */
public class DrillOriminFT implements DrillInterface {

	@Override
	public double getDiameter(double zInMm) {
		return 0.5;
	}

	@Override
	public double getMaxZInMm() {
		return 2.0;
	}
	
}

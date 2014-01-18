package st.tori.cnc.stencil.gcode.drill;

/*
 * http://www.originalmind.co.jp/goods/07960
 */
public class DrillOriminVC implements DrillInterface {

	@Override
	public double getDiameter(double zInMm) {
		return Math.min(zInMm*Math.tan(Math.PI*53.0/360.0)*2, 0.8);
	}

	@Override
	public double getMaxZInMm() {
		return 3.5;
	}
	
}

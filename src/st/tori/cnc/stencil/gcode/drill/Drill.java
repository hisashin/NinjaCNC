package st.tori.cnc.stencil.gcode.drill;

public interface Drill {

	float getDiameter(double zInMm);
	double getMaxZInMm();

	public static Drill ORIMIN_FT = new Drill(){
		@Override
		public float getDiameter(double zInMm) {
			return 0.5f;
		}

		@Override
		public double getMaxZInMm() {
			return 2.0;
		}
	};
	
	public static Drill ORIMIN_VC = new Drill(){
		@Override
		public float getDiameter(double zInMm) {
			return (float)Math.min(zInMm*Math.tan(Math.PI*53.0/360.0)*2, 0.8);
		}

		@Override
		public double getMaxZInMm() {
			return 3.5;
		}
	};
	
}

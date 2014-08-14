package st.tori.cnc.stencil.util;

public class DegUtil {

	public static double toRad(double deg) {
		return 2*Math.PI*deg/360.0;
	}
	public static double cos(double deg){
		return Math.cos(toRad(deg));
	}
	public static double sin(double deg){
		return Math.sin(toRad(deg));
	}
	public static double tan(double deg){
		return Math.tan(toRad(deg));
	}

}

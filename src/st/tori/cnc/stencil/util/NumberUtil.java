package st.tori.cnc.stencil.util;

public class NumberUtil {

	/*
	 * Extend num string to specified length by adding "0" on its head
	 */
	public static final String toString(int num, int length) {
		String str = Integer.toString(num);
		while(str.length()<length)str = "0"+str;
		return str;
	}

	/*
	 * Return double value as string with 3 decimal places
	 */
	public static String toGCodeValue(double val) {
		return String.format("%.3f", val);
	}
	
}

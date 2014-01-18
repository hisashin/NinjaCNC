package st.tori.cnc.stencil.gcode.shape;

public class ShapePolygon {

	protected double[][] xyArray;
	
	public ShapePolygon(double[][] xyArray) {
		this.xyArray = xyArray;
	}
	
	public double[][] getArray(){	return xyArray;	}
	
}

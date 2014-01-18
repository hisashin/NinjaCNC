package st.tori.cnc.stencil.gcode.shape;

public class ShapeSquare extends ShapePolygon {

	public ShapeSquare(double x0, double y0, double x1, double y1) {
		super(new double[][]{
			new double[]{x0,y0},
			new double[]{x1,y0},
			new double[]{x1,y1},
			new double[]{x0,y1},
			new double[]{x0,y0},
		});
	}
	
}

package st.tori.cnc.stencil.test;

import st.tori.cnc.stencil.canvas.shape.Square;
import st.tori.cnc.stencil.gcode.drill.DrillOriminVC;
import st.tori.cnc.stencil.gcode.parser.GCode;

public class GCodeSampleLevistoneTCream extends GCode {

	public GCodeSampleLevistoneTCream() {
		super();
		initialize(new DrillOriminVC(), 5.0, 0.5, -0.15, 100.0, 300.0);
		add(new Square(-0.1, -0.1, 1.37, 1.37));
		add(new Square(2.735, -0.1, 3.935, 1.37));
		add(new Square(5.3, -0.1, 6.77, 1.37));
		add(new Square(8.735, 3.185, 9.436, 4.186));
		add(new Square(8.735, 4.687, 9.436, 5.687));
		add(new Square(10.237, 4.687, 10.937, 5.687));
		add(new Square(10.237, 3.185, 10.937, 4.186));
		add(new Square(10.237, 7.186, 10.937, 8.186));
		add(new Square(8.735, 7.186, 9.436, 8.186));
		add(new Square(7.237, 7.186, 7.937, 8.186));
		add(new Square(5.736, 7.186, 6.436, 8.186));
		add(new Square(5.296, 8.697, 5.997, 10.398));
		add(new Square(6.294, 8.697, 6.995, 10.398));
		add(new Square(7.295, 8.697, 7.996, 10.398));
		add(new Square(8.296, 8.697, 8.996, 10.398));
		add(new Square(9.297, 8.697, 9.997, 10.398));
		add(new Square(10.595, 9.871, 12.296, 10.571));
		add(new Square(10.595, 10.872, 12.296, 11.572));
		add(new Square(10.595, 11.872, 12.296, 12.573));
		add(new Square(10.595, 12.873, 12.296, 13.573));
		add(new Square(10.595, 13.871, 12.296, 14.572));
		add(new Square(10.595, 14.872, 12.296, 15.572));
		add(new Square(9.297, 15.047, 9.997, 16.748));
		add(new Square(8.296, 15.047, 8.996, 16.748));
		add(new Square(7.295, 15.047, 7.996, 16.748));
		add(new Square(6.294, 15.047, 6.995, 16.748));
		add(new Square(5.296, 15.047, 5.997, 16.748));
		add(new Square(4.295, 15.047, 4.996, 16.748));
		add(new Square(3.295, 15.047, 3.995, 16.748));
		add(new Square(2.296, 15.047, 2.997, 16.748));
		add(new Square(1.296, 15.047, 1.996, 16.748));
		add(new Square(0.295, 15.047, 0.995, 16.748));
		add(new Square(0.295, 8.697, 0.995, 10.398));
		add(new Square(1.296, 8.697, 1.996, 10.398));
		add(new Square(2.296, 8.697, 2.997, 10.398));
		add(new Square(3.295, 8.697, 3.995, 10.398));
		add(new Square(4.295, 8.697, 4.996, 10.398));
		add(new Square(3.856, 6.874, 4.556, 7.874));
		add(new Square(2.355, 6.874, 3.055, 7.874));
		add(new Square(2.735, 5.3, 3.935, 6.77));
		add(new Square(5.3, 5.3, 6.77, 6.77));
		add(new Square(-0.1, 5.3, 1.37, 6.77));
		finalize();
	}

}

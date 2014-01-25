package st.tori.cnc.stencil.test;

import st.tori.cnc.stencil.canvas.shape.Square;
import st.tori.cnc.stencil.gcode.drill.Drill;
import st.tori.cnc.stencil.gcode.parser.GCode;

public class GCodeSampleLevistoneTCream extends GCode {

	public GCodeSampleLevistoneTCream() {
		super(Drill.ORIMIN_VC);
		initialize(5.0, 0.5, -0.15, 100.0, 300.0);
		float stroke = 0.2f;
		add(new Square(-0.1, -0.1, 1.37, 1.37, stroke));
		add(new Square(1.336, 2.236, 2.336, 2.936, stroke));
		add(new Square(2.835, 2.236, 3.835, 2.936, stroke));
		add(new Square(4.336, 2.236, 5.336, 2.936, stroke));
		add(new Square(4.336, 3.737, 5.336, 4.437, stroke));
		add(new Square(2.835, 3.737, 3.835, 4.437, stroke));
		add(new Square(1.336,3.737 , 2.336,4.437 , stroke));
		add(new Square(2.735,5.300 , 3.935,6.770 , stroke));
		add(new Square(2.355,6.874 , 3.055,7.874 , stroke));
		add(new Square(3.856,6.874 , 4.556,7.874 , stroke));
		add(new Square(4.295,8.697 , 4.996,10.398 , stroke));
		add(new Square(3.295,8.697 , 3.995,10.398 , stroke));
		add(new Square(2.296,8.697 , 2.997,10.398 , stroke));
		add(new Square(1.296,8.697 , 1.996,10.398 , stroke));
		add(new Square(0.295,8.697 , 0.995,10.398 , stroke));
		add(new Square(-0.100,5.300 , 1.370,6.770 , stroke));
		add(new Square(5.300,5.300 , 6.770,6.770 , stroke));
		add(new Square(5.736,7.186 , 6.436,8.186 , stroke));
		add(new Square(7.237,7.186 , 7.937,8.186 , stroke));
		add(new Square(7.133,6.068 , 8.133,6.769 , stroke));
		add(new Square(7.133,4.570 , 8.133,5.270 , stroke));
		add(new Square(8.735,4.687 , 9.436,5.687 , stroke));
		add(new Square(8.735,3.185 , 9.436,4.186 , stroke));
		add(new Square(10.237,3.185 , 10.937,4.186 , stroke));
		add(new Square(10.237,4.687 , 10.937,5.687 , stroke));
		add(new Square(9.297,8.697 , 9.997,10.398 , stroke));
		add(new Square(8.296,8.697 , 8.996,10.398 , stroke));
		add(new Square(7.295,8.697 , 7.996,10.398 , stroke));
		add(new Square(6.294,8.697 , 6.995,10.398 , stroke));
		add(new Square(5.296,8.697 , 5.997,10.398 , stroke));
		add(new Square(10.595,9.871 , 12.296,10.571 , stroke));
		add(new Square(10.595,10.872 , 12.296,11.572 , stroke));
		add(new Square(10.595,11.872 , 12.296,12.573 , stroke));
		add(new Square(10.595,12.873 , 12.296,13.573 , stroke));
		add(new Square(10.595,13.871 , 12.296,14.572 , stroke));
		add(new Square(10.595,14.872 , 12.296,15.572 , stroke));
		add(new Square(9.297,15.047 , 9.997,16.748 , stroke));
		add(new Square(8.296,15.047 , 8.996,16.748 , stroke));
		add(new Square(7.295,15.047 , 7.996,16.748 , stroke));
		add(new Square(6.294,15.047 , 6.995,16.748 , stroke));
		add(new Square(5.296,15.047 , 5.997,16.748 , stroke));
		add(new Square(4.295,15.047 , 4.996,16.748 , stroke));
		add(new Square(3.295,15.047 , 3.995,16.748 , stroke));
		add(new Square(2.296,15.047 , 2.997,16.748 , stroke));
		add(new Square(1.296,15.047 , 1.996,16.748 , stroke));
		add(new Square(0.295,15.047 , 0.995,16.748 , stroke));
		add(new Square(2.735,-0.100 , 3.935,1.370 , stroke));
		add(new Square(5.300,-0.100 , 6.770,1.370 , stroke));
		finalize();
	}

}

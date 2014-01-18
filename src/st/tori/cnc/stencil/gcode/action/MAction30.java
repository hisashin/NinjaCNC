package st.tori.cnc.stencil.gcode.action;

/*
	M30
	End of program, with return to program top
	M	T
	Today M30 is considered the standard program-end code, 
	and will return execution to the top of the program. 
	Today most controls also still support the original program-end code, M02, 
	usually by treating it as equivalent to M30. 
	
	Additional info: Compare M02 with M30. First, M02 was created, 
	in the days when the punched tape was expected to be short enough to be 
	spliced into a continuous loop (which is why on old controls, 
	M02 triggered no tape rewinding).[4] The other program-end code, 
	M30, was added later to accommodate longer punched tapes, 
	which were wound on a reel and thus needed rewinding before another cycle could start.
	[4] On many newer controls, there is no longer a difference in how the codes are executed—both act like M30.
 */
public class MAction30 extends MAction {

	@Override
	protected int getMIndex() {	return 30;	}

}

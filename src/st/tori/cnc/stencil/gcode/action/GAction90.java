package st.tori.cnc.stencil.gcode.action;

/*
	G90
	Absolute programming	
	M	T (B)	
	Positioning defined with reference to part zero.
	Milling: Always as above.
	Turning: Sometimes as above (Fanuc group type B and similarly designed), 
	but on most lathes (Fanuc group type A and similarly designed), 
	G90/G91 are not used for absolute/incremental modes. 
	Instead, U and W are the incremental addresses and X and Z are the absolute addresses. 
	On these lathes, G90 is instead a fixed cycle address for roughing.
 */
public class GAction90 extends GAction {
	
	@Override
	protected int getGIndex() {	return 90;	}
	@Override
	public boolean isFundamental() {	return true;	}

	public GAction90(GCode gCode) {
		super(gCode);
	}

}

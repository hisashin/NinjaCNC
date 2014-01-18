package st.tori.cnc.stencil.gcode.action;

/*
	M03
	Spindle on (clockwise rotation)
	M	T	
	The speed of the spindle is determined by the address S,
	in either revolutions per minute (G97 mode; default) or surface feet per minute 
	or [surface] meters per minute (G96 mode [CSS] under either G20 or G21).
	 The right-hand rule can be used to determine which direction is clockwise 
	 and which direction is counter-clockwise.
	Right-hand-helix screws moving in the tightening direction 
	(and right-hand-helix flutes spinning in the cutting direction) are defined 
	as moving in the M03 direction, and are labeled "clockwise" by convention. 
	The M03 direction is always M03 regardless of local vantage point and local CW/CCW distinction.
 */
public class MAction03 extends MAction {

	@Override
	protected int getMIndex() {	return 3;	}

}

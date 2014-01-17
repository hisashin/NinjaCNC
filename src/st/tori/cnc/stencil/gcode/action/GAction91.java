package st.tori.cnc.stencil.gcode.action;

/*
	G91
	Incremental programming
	M	T (B)
	Positioning defined with reference to previous position.
	Milling: Always as above.
	Turning: Sometimes as above (Fanuc group type B and similarly designed), 
	but on most lathes (Fanuc group type A and similarly designed), 
	G90/G91 are not used for absolute/incremental modes. 
	Instead, U and W are the incremental addresses and X and Z are the absolute addresses. 
	On these lathes, G90 is a fixed cycle address for roughing.
 */
public class GAction91 extends GAction {

}

package st.tori.cnc.stencil.gcode.exception;

public class TooDeepCutHeightException extends GCodeException {

	public TooDeepCutHeightException(double maxZInMm, double deepCutHeight) {
		super("Cut Height "+deepCutHeight+" is too deep.Max is "+maxZInMm);
	}

}

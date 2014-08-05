package st.tori.cnc.stencil.gerber.parser;


public class GerberLevel {

	public enum GERBER_LEVEL_POLARITY {
		DARK,
		CLEAR,
	}

	private GERBER_LEVEL_POLARITY polarity;
	
	public GerberLevel(GERBER_LEVEL_POLARITY polarity) {
		this.polarity = polarity;
	}
	
}
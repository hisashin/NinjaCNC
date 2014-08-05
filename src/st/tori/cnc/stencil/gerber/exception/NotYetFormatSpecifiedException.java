package st.tori.cnc.stencil.gerber.exception;

public class NotYetFormatSpecifiedException extends GerberException {

	public NotYetFormatSpecifiedException() {
		super("Format not yet specified by %FS");
	}
	
}

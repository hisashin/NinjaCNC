package st.tori.cnc.stencil.canvas.exception;

public class UnknownDrawableException extends DrawException {

	public UnknownDrawableException(Object unknown) {
		super("Cannot draw "+unknown.getClass().getSimpleName()+".Add codes to AppletManager.draw");
	}

}

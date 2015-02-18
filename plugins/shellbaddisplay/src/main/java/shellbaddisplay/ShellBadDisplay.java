package shellbaddisplay;

import snakecore.interfaces.Display;

public class ShellBadDisplay implements Display{

	@Override
	public void sayHello() {
		System.err.println("Hello");
	}
}

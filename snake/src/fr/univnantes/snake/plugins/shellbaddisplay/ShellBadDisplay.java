package fr.univnantes.snake.plugins.shellbaddisplay;

import fr.univnantes.snake.plugins.snakecore.interfaces.Display;

public class ShellBadDisplay implements Display{

	@Override
	public void sayHello() {
		System.err.println("Hello");
	}
}

package fr.univnantes.snake.shellbaddisplay;

import fr.univnantes.snake.snakecore.interfaces.Display;

public class ShellBadDisplay implements Display{

	@Override
	public void sayHello() {
		System.err.println("Hello");
	}
}

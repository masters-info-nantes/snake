package fr.univnantes.snake.framework;

import fr.univnantes.snake.framework.PluginLoader;

public abstract class MGSApplication {
	public PluginLoader pluginsLoader;
	
	public abstract void run();
}

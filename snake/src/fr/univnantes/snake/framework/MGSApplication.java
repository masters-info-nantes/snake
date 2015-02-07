package fr.univnantes.snake.framework;

import fr.univnantes.snake.framework.PluginLoader;

public abstract class MGSApplication {
	public PluginLoader pluginsManager;
	
	public abstract void run();
}

package fr.univnantes.snake.framework;

import fr.univnantes.snake.framework.PluginLoader;

public abstract class MGSApplication {
	protected PluginLoader pluginsLoader;
	protected Plugin currentPlugin;
	
	public abstract void run();
}

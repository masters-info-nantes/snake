package fr.univnantes.mgsframework;

import fr.univnantes.mgsframework.PluginLoader;

public abstract class MGSApplication {
	protected PluginLoader pluginsLoader;
	protected Plugin currentPlugin;
	
	public abstract void run();
}

package fr.univnantes.mgsframework;

public abstract class MGSApplication {
	
	protected PluginLoader pluginsLoader;
	protected Plugin currentPlugin;
	
	public abstract void run();
}

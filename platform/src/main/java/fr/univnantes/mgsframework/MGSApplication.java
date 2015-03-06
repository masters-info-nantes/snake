package fr.univnantes.mgsframework;

public abstract class MGSApplication {
	
	protected PluginLoader pluginsLoader;
	protected RunnablePlugin currentPlugin;
	
	public abstract void run();
}

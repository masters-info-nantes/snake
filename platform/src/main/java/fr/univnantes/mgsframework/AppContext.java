package fr.univnantes.mgsframework;

public class AppContext {
	private PluginLoader pluginsLoader;
	private Plugin currentPlugin;

	public final static AppContext instance = new AppContext();

	public PluginLoader getPluginsLoader(){
		return this.pluginsLoader;
	}

	public Plugin getCurrentPlugin(){
		return this.currentPlugin;
	}

	protected void setPluginsLoader(PluginLoader pluginsLoader){
		this.pluginsLoader = pluginsLoader;
	}

	protected void setCurrentPlugin(Plugin currentPlugin){
		this.currentPlugin = currentPlugin;
	}	
}
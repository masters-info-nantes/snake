package snakecore;

import java.io.IOException;
import java.util.Collection;

import fr.univnantes.mgsframework.Framework;
import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.PluginLoader;
import fr.univnantes.mgsframework.RunnablePlugin;

public class Model {
	private PluginLoader pluginLoader;
	private RunnablePlugin currentPlugin;
	
	public Model(PluginLoader pluginLoader,RunnablePlugin currentPlugin){
		this.pluginLoader = pluginLoader;
		this.currentPlugin = currentPlugin;
	}
	
	public String getPluginsPath(){
		return Framework.getInstance().getPluginsPath();
	}
	
	public String getStartPluginName(){
		return Framework.getInstance().getStartPluginName();
	}

	public Collection<RunnablePlugin> getMainPluginList() {
		return this.pluginLoader.getRunnablePluginsList();
	}
	
	public Collection<Plugin> getPluginsByCategory(String category){
		return this.pluginLoader.getClassicPluginsByCategory(category);
	}

	public Collection<String> getInterfacesCurrentPlugin(){
		return this.currentPlugin.getCategories();
	}
	
	public void runMainPlugin(RunnablePlugin plugin) throws IOException {
		MGSApplication app = this.pluginLoader.loadApplication(plugin.getName());
		app.run();
	}
}

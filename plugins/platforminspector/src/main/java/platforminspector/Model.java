package platforminspector;

import java.util.Collection;

import fr.univnantes.mgsframework.Framework;
import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.PluginLoader;
import fr.univnantes.mgsframework.RunnablePlugin;

public class Model {
	private PluginLoader pluginLoader;
	
	public Model(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
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
}

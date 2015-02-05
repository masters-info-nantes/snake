package fr.univnantes.snake.framework;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PluginLoader {
	
	private String pluginsPath;
	private Map<String, Plugin> pluginsList;
	
	public PluginLoader(){
		this.pluginsPath = "";
		this.pluginsList = new HashMap<String, Plugin>();
	}

	public Set<String> listPlugins(){
		
		if(!this.pluginsList.isEmpty()){
			return this.pluginsList.keySet();
		}
		
		File pluginsDir = new File(this.pluginsPath);
		
		if (pluginsDir != null && pluginsDir.exists() && pluginsDir.isDirectory()){
	        File[] files = pluginsDir.listFiles();	   
	        
	        for (int i = 0; i < files.length; i++) {
	        	File currentPlugin = files[i];

	        	if(currentPlugin.isDirectory()){
	        		this.pluginsList.put(currentPlugin.getName(), new Plugin(currentPlugin.getName()));
	        	}
	        }
		}
		
		return this.pluginsList.keySet();
	}
	
	public String getPluginsPath() {
		return pluginsPath;
	}

	public void setPluginsPath(String pluginsPath) {
		this.pluginsPath = pluginsPath;
	}
}

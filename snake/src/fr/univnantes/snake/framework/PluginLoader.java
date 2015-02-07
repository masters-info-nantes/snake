package fr.univnantes.snake.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Plugin loader used by framework and application
 * To hide methods for applications set them protected
 * @author jeremy
 *
 */
public class PluginLoader {
	
	private String pluginsPath;
	private Map<String, Plugin> pluginsList;
	private URLClassLoader loader;
	
	public PluginLoader(){
		this.pluginsPath = "";
		this.pluginsList = new HashMap<String, Plugin>();
	}

	/**
	 * List plugins in plugins directory given in framework
	 * configuration file with pluginspath parameter
	 * 
	 * @return plugins directory name
	 */
	protected void loadPluginsList(){
		
		List<URL> pluginsPaths = new ArrayList<URL>();
		
		File pluginsDir = new File(this.pluginsPath);
		
		if (pluginsDir != null && pluginsDir.exists() && pluginsDir.isDirectory()){
	        File[] files = pluginsDir.listFiles();	   
	        
	        for (int i = 0; i < files.length; i++) {
	        	File currentPlugin = files[i];

	        	if(currentPlugin.isDirectory()){
	        		this.pluginsList.put(currentPlugin.getName(), new Plugin(currentPlugin.getName()));
	        		try {
						pluginsPaths.add(new URL("file://" + currentPlugin.getAbsolutePath() + "/"));
					} 
	        		catch (MalformedURLException e) {
	        			System.err.println("Cannot access to " + currentPlugin.getAbsolutePath() + "plugin");
	        			e.printStackTrace();
	        		}
	        	}
	        }
		}
		
		URL[] pathArray = new URL[pluginsPaths.size()];
		pathArray = pluginsPaths.toArray(pathArray);
	
		this.loader = new URLClassLoader(pathArray);
	}

	public Set<String> givePluginsList(){
		return this.pluginsList.keySet();
	}
	
	/**
	 * Load main class of a runnable plugin. The main class is given
	 * in plugin configuration file with mainClass parameter
	 * @param pluginName plugin directory name
	 * @return instance of plugin main class
	 * @throws IOException
	 */
	protected MGSApplication loadApplication(String pluginName) throws IOException{
		String mainClass = this.loadPluginConfiguration(pluginName);
		
    	Class<?> classe;
		try {
			classe = this.loader.loadClass(mainClass);
		} catch (ClassNotFoundException e) {
			System.err.println("Main class " + mainClass + " for plugin " + pluginName + " not found");
			e.printStackTrace();
			return null;
		}
		
    	Object objet;
		try {
			objet = classe.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Cannot load " + mainClass + " class");
			e.printStackTrace();
			return null;
		}

    	if(!MGSApplication.class.isAssignableFrom(classe)){
    		return null;
    	}
    	
    	MGSApplication application = (MGSApplication)objet;
    	application.pluginsManager = this;
    	
    	return application;
	}
	
	
	/**
	 * Load plugin configuration file called plugin.txt and 
	 * located in plugin directory
	 * @param pluginName plugin directory
	 * @return name of mainClass for the plugin
	 * @throws IOException
	 */
	private String loadPluginConfiguration(String pluginName) throws IOException {
		FileReader configFile;
		try {
			configFile = new FileReader(this.pluginsPath + "/" + pluginName + "/plugin.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException("Config file missing for " + pluginName + "plugin");
		} 
		
		Properties configReader = new Properties();
		try {
			configReader.load(configFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Malformed configuration file for plugin " + pluginName);
		}
		
		return configReader.getProperty("mainClass");
	}

	protected String getPluginsPath() {
		return pluginsPath;
	}

	protected void setPluginsPath(String pluginsPath) {
		this.pluginsPath = pluginsPath;
	}
}

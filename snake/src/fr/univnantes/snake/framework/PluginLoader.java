package fr.univnantes.snake.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	private Map<String, Plugin> runnablePlugins;
	private Map<String, Plugin> classicPlugins;	
	private Set<String> mainPluginInterfaces;
	private URLClassLoader loader;
	
	public PluginLoader(){
		this.pluginsPath = "";
		this.runnablePlugins = new HashMap<String, Plugin>();
		this.classicPlugins = new HashMap<String, Plugin>();	
		this.mainPluginInterfaces = new HashSet<String>();
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
	        		Plugin plugin = null;
					try {
						plugin = this.loadPluginConfiguration(currentPlugin.getName());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
						continue;
					}
					
					System.out.println("Plugin " + plugin.getName() + " loaded.");
					
					if(plugin.isRunnable()){
		        		this.runnablePlugins.put(currentPlugin.getName(), plugin);						
					}
					else {
						this.classicPlugins.put(currentPlugin.getName(), plugin);	
					}

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
	
	/**
	 * Load main class of a runnable plugin. The main class is given
	 * in plugin configuration file with mainClass parameter
	 * @param pluginName plugin directory name
	 * @return instance of plugin main class
	 * @throws IOException
	 */
	protected MGSApplication loadApplication(String pluginName) throws IOException{
		Plugin plugin = this.loadPluginConfiguration(pluginName);
		
    	Class<?> classe;
    	Object objet;
    	
		try {
			classe = this.loader.loadClass(plugin.getMainClass());
			objet = classe.newInstance();			
		} 
		catch (ClassNotFoundException e) {
			System.err.println("Main class " + plugin.getMainClass() + " for plugin " + pluginName + " not found");
			e.printStackTrace();
			return null;
		}
		catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Cannot load " + plugin.getMainClass() + " class");
			e.printStackTrace();
			return null;
		}		

    	if(!MGSApplication.class.isAssignableFrom(classe)){
    		throw new IOException("Cannot load " + plugin.getMainClass() + " for in " + pluginName + " because it does not extends from MGSApplication class");
    	}
    	
    	MGSApplication application = (MGSApplication)objet;
    	application.pluginsLoader = this;
    	
    	this.loadApplicationInterfaces(plugin);
    	
    	return application;
	}
	
	/**
	 * Load interface for a given plugin which is an Application
	 * @param plugin Runnable plugin (extends from MGSApplication)
	 */
	private void loadApplicationInterfaces(Plugin plugin) {
		File interfaceDir = new File(this.pluginsPath + "/" + plugin.getName() + "/interfaces/");
		
		if (interfaceDir != null && interfaceDir.exists() && interfaceDir.isDirectory()){
	        File[] interfaces = interfaceDir.listFiles();
	        
	        for (int i = 0; i < interfaces.length; i++) {
	        	File currentInterface = interfaces[i];

	        	if(currentInterface.isFile() && currentInterface.getName().endsWith(".java")){
	        		String interfaceName = currentInterface.getName();
	        		this.mainPluginInterfaces.add(interfaceName.substring(0, interfaceName.lastIndexOf('.')));
	        	}
	        }
		}
	}

	/**
	 * Load plugin configuration file called plugin.txt and 
	 * located in plugin directory
	 * @param pluginName plugin directory
	 * @return plugin configuration as Plugin object
	 * @throws IOException
	 */
	private Plugin loadPluginConfiguration(String pluginName) throws IOException {
		FileReader configFile;
		Properties configReader = new Properties();
		
		try {
			configFile = new FileReader(this.pluginsPath + "/" + pluginName + "/plugin.txt");
			configReader.load(configFile);			
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Config file missing for " + pluginName + "plugin. Please add plugin.txt file in plugin directory.");
		} 
		catch (IOException e) {
			throw new IOException("Malformed configuration file for plugin " + pluginName + ". Please check your plugin.txt file.");
		}
		
		String category  = configReader.getProperty("category"),
			   mainClass = configReader.getProperty("mainClass");
				
		Plugin plugin = null;
		
		if(mainClass != null){
			plugin = new Plugin(pluginName, "MGSApplication", mainClass);
		}
		else if(category == null){
			throw new IOException("Unable to load " + pluginName + " plugin. Please give a mainClass or a category in plugin.txt.");
		}
		else {
			plugin = new Plugin(pluginName, category);			
		}
		
		return plugin;
	}

	protected Set<String> getRunnablePluginsList(){
		return this.runnablePlugins.keySet();
	}

	// Avoid dependancies
	public Set<String> getClassicPluginsList(){
		return this.classicPlugins.keySet();
	}

	public Collection<Plugin> getClassicPlugins(){
		return this.classicPlugins.values();
	}
	
	public Set<String> getMainPluginCategories(){
		return this.mainPluginInterfaces;
	}
	
	protected String getPluginsPath() {
		return pluginsPath;
	}

	protected void setPluginsPath(String pluginsPath) {
		this.pluginsPath = pluginsPath;
	}
}

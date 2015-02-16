package fr.univnantes.mgsframework;

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
	
	private final static String mainPluginCategory = "fr.univnantes.mgsframework.MGSApplication";

	private String pluginsPath;
	private Map<String, Plugin> runnablePlugins;
	private Map<String, Plugin> classicPlugins;
	
	private Set<String> mainPluginInterfaces;
	private Map<String, List<Plugin>> pluginsByCategories;
	private URLClassLoader loader;
	
	public PluginLoader(){
		this.pluginsPath = "";
		this.runnablePlugins = new HashMap<String, Plugin>();
		this.classicPlugins = new HashMap<String, Plugin>();
		
		this.mainPluginInterfaces = new HashSet<String>();
		this.pluginsByCategories = new HashMap<String, List<Plugin>>();
	}

	/**
	 * List plugins in plugins directory given in framework
	 * configuration file with pluginspath parameter
	 * 
	 * @return plugins directory name
	 */
	protected void loadPluginsList(){
		
		System.out.println("Plugins loading");
		
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
						System.err.println("* " + e1.getMessage());
						continue;
					}
					
					System.out.println("* Plugin " + plugin.getName() + " loaded.");
					
					if(plugin.isRunnable()){
		        		this.runnablePlugins.put(currentPlugin.getName(), plugin);						
					}
					else {
						this.classicPlugins.put(currentPlugin.getName(), plugin);
						
						List<Plugin> sameCategoryPlugins = this.pluginsByCategories.get(plugin.getCategory());
						if(sameCategoryPlugins == null){
							sameCategoryPlugins = new ArrayList<Plugin>();
						}
						
						sameCategoryPlugins.add(plugin);
						if(sameCategoryPlugins.size() == 1){
							this.pluginsByCategories.put(plugin.getCategory(), sameCategoryPlugins);
						}
					}
	        	}
	        }
		}

		URL path = null;
		try {
			path = new URL("file:///Volumes/Data/Projets/snake/maven/platform/ressources/snake-0.1.jar");
		} 
		catch (MalformedURLException e) {
			System.err.println("Cannot access to jar file");
			e.printStackTrace();
		}		
			
		//URL currentPath = ((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs()[0];
		this.loader = new URLClassLoader(new URL[]{ path/*, currentPath*/ }, MGSApplication.class.getClassLoader());
	}
	
	/**
	 * Load plugin configuration file called plugin.txt and 
	 * located in plugin directory
	 * @param pluginName plugin directory
	 * @return plugin configuration as Plugin object
	 * @throws IOException report to the exception message
	 */
	private Plugin loadPluginConfiguration(String pluginName) throws IOException {
		FileReader configFile;
		Properties configReader = new Properties();
		
		try {
			configFile = new FileReader(this.pluginsPath + "/" + pluginName + "/plugin.txt");
			configReader.load(configFile);			
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Config file missing for " + pluginName + " plugin. Please add plugin.txt file in plugin directory.");
		} 
		catch (IOException e) {
			throw new IOException("Malformed configuration file for plugin " + pluginName + ". Please check your plugin.txt file.");
		}
		
		String runnableString = configReader.getProperty("runnable"),
			   category  = configReader.getProperty("category"),
			   mainClass = configReader.getProperty("mainClass"),
			   description = configReader.getProperty("description");
		
		boolean runnable = "1".equals(runnableString) ? true : Boolean.parseBoolean(runnableString);
		
		if(!runnable && category == null){
			throw new IOException("Unable to load " + pluginName + " plugin. Please provide category for non runnable plugin in plugin.txt.");		
		}
		
		Plugin plugin = null;
		
		if(runnable && category == null){
			plugin = new Plugin(pluginName, PluginLoader.mainPluginCategory, mainClass, runnable, description);
		}
		else {
			plugin = new Plugin(pluginName, category, mainClass, runnable, description);			
		}
		
		return plugin;
	}	
	
	/**
	 * Load main class of a runnable plugin. The main class is given
	 * in plugin configuration file with mainClass parameter
	 * @param pluginName plugin directory name
	 * @return instance of plugin main class
	 * @throws IOException report to the exception message
	 */
	protected MGSApplication loadApplication(String pluginName) throws IOException{
		Plugin plugin = this.loadPluginConfiguration(pluginName);
		
    	MGSApplication application = (MGSApplication) this.loadPlugin(plugin);
    	application.pluginsLoader = this;
    	application.currentPlugin = plugin;
    	
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
	 * Returns instance of the given plugin main class. Exceptions ensures that
	 * the given object is a subtype of the plugin category
	 * @param plugin plugin to load main class from
	 * @return instance of the plugin main class
	 * @throws IOException report to the exception message
	 */
	public Object loadPlugin(Plugin plugin) throws IOException{
		Class<?> mainClass, implementedClass =null;
    	Object objet = null;

/*
for(URL url: loader.getURLs()){
System.out.println(url);	
}
*/
		try {
			//mainClass = this.loader.loadClass(plugin.getMainClass());
			mainClass = Class.forName(plugin.getMainClass(), true, loader);
			implementedClass = this.loader.loadClass(plugin.getCategory());			
			objet = mainClass.newInstance();			
		} 
		catch (ClassNotFoundException e) {
			throw new IOException("Class " + plugin.getMainClass() + " for plugin " + plugin.getName() + " not found");
		}
		catch (InstantiationException e) {
			throw new IOException("Cannot load " + plugin.getMainClass() + " class");
		}		
		catch (IllegalAccessException e) {
			throw new IOException("Cannot load " + plugin.getMainClass() + " class");			
		}

		System.out.println("category: " + plugin.getCategory());
		System.out.println("super class: " + mainClass.getSuperclass());
		System.out.println("assignable from: " + implementedClass.isAssignableFrom(mainClass));
	   	if(!implementedClass.isAssignableFrom(mainClass)){
    		throw new IOException("Cannot load " + plugin.getMainClass() + " mainClass for " + plugin.getName() + " plugin because it does not implements given category interface " + implementedClass.getName());
    	}
		
		return objet;
	}
	
	public String getPluginsPath() {
		return pluginsPath;
	}

	protected void setPluginsPath(String pluginsPath) {
		this.pluginsPath = pluginsPath;
	}
	
	/**
	 * Returns runnable plugins list
	 * @return runnable plugins list
	 */
	protected Set<String> getRunnablePluginsList(){
		return this.runnablePlugins.keySet();
	}

	/**
	 * Returns all classic plugins.
	 * @return all classic plugins
	 */
	public Collection<Plugin> getClassicPlugins(){
		return this.classicPlugins.values();
	}
	
	/**
	 * Returns categories definied by the current main plugin
	 * @return categories definied by main plugin
	 */
	public Set<String> getMainPluginCategories(){
		return this.mainPluginInterfaces;
	}
	
	/**
	 * Returns all plugins from the given category
	 * @param category category of returned plugins
	 * @return all plugins with the given category
	 */
	public List<Plugin> getClassicPluginsByCategory(String category){
		return this.pluginsByCategories.get(category);
	}
}

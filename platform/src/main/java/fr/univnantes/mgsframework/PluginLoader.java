package fr.univnantes.mgsframework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Plugin loader used by framework and application
 * To hide methods for applications set them protected
 * @author jeremy
 *
 */
public class PluginLoader {
	
	private final static String mainPluginCategory = "fr.univnantes.mgsframework.MGSApplication";

	private String pluginsPath;
	private Map<String, RunnablePlugin> runnablePlugins;
	private Map<String, Plugin> classicPlugins;
	private Map<String, List<Plugin>> pluginsByCategories;
	
	private URLClassLoader loader;
	
	public PluginLoader(){
		this.pluginsPath = Framework.getInstance().getPluginsPath();
		
		this.runnablePlugins = new HashMap<String, RunnablePlugin>();
		this.classicPlugins = new HashMap<String, Plugin>();
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
		List<URL> pluginsPaths = new ArrayList<URL>();
		File pluginsDir = new File(this.pluginsPath);
		
		if (pluginsDir != null && pluginsDir.exists() && pluginsDir.isDirectory()){
	        File[] files = pluginsDir.listFiles();	   
	        
	        for (int i = 0; i < files.length; i++) {
	        	File currentPlugin = files[i];

	        	if(currentPlugin.isFile() && currentPlugin.getName().endsWith(".jar")){
	        		Plugin plugin = null;
					try {
						plugin = this.loadPluginConfiguration(currentPlugin.getName());
					} catch (IOException e1) {
						System.err.println("* " + e1.getMessage());
						continue;
					}
					
					System.out.println("* Plugin " + plugin.getName() + " loaded.");

					if(plugin.isRunnable()){
		        		this.runnablePlugins.put(currentPlugin.getName(), (RunnablePlugin) plugin);
		        		this.loadPluginInterfaces((RunnablePlugin) plugin);
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
					
	        		try {
						pluginsPaths.add(new URL("file://" + currentPlugin.getAbsolutePath()));
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
	
		this.loader = new URLClassLoader(pathArray, PluginLoader.class.getClassLoader());
	}
	
	/**
	 * Load plugin configuration file called plugin.txt and 
	 * located in plugin directory
	 * @param pluginName plugin directory
	 * @return plugin configuration as Plugin object
	 * @throws IOException report to the exception message
	 */
	@SuppressWarnings("resource")
	private Plugin loadPluginConfiguration(String pluginName) throws IOException {
		
		// If the plugin was allready loaded
		Plugin mainPlugin = (Plugin) this.classicPlugins.get(pluginName),
			   classicPlugin = (Plugin) this.runnablePlugins.get(pluginName);
	
		if(mainPlugin != null){
			return mainPlugin;
		}
		else if(classicPlugin != null){
			return classicPlugin;
		}
		
		// Load plugin configuration
		InputStream configFile;
		Properties configReader = new Properties();

		try {			
			ZipFile jarFile = new ZipFile(this.pluginsPath + "/" + pluginName);
			ZipEntry settingsFile = jarFile.getEntry("plugin.txt");
			
			if(settingsFile == null){
				throw new FileNotFoundException("Config file missing for " + pluginName + " plugin. Please add plugin.txt file in plugin directory.");				
			}
			
			configFile = jarFile.getInputStream(settingsFile);			
			configReader.load(configFile);			
		} 
		catch (IOException e) {
			throw new IOException("Malformed configuration file for plugin " + pluginName + ". Please check your plugin.txt file.");
		}
		
		String runnableString = configReader.getProperty("runnable"),
			   category  = configReader.getProperty("category"),
			   mainClass = configReader.getProperty("mainClass"),
			   description = configReader.getProperty("description");
		
		boolean runnable = Boolean.parseBoolean(runnableString);
		
		if(!runnable && category == null){
			throw new IOException("Unable to load " + pluginName + " plugin. Please provide category for non runnable plugin in plugin.txt.");		
		}
		
		Plugin plugin = null;
		
		if(runnable){
			String categoryParam = (category == null) ? PluginLoader.mainPluginCategory : category;
			plugin = new RunnablePlugin(pluginName, categoryParam, mainClass, runnable, description);
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
	public MGSApplication loadApplication(String pluginName) throws IOException{
		Plugin plugin = null;
		
		try {
			plugin = this.loadPluginConfiguration(pluginName);
		}
		catch(IOException e){
			throw new IOException("Plugin " + pluginName + " does not exists");
		}

    	MGSApplication application = (MGSApplication) this.loadPlugin(plugin);
    	application.currentPlugin = (RunnablePlugin) plugin;
    	application.pluginsLoader = this;
    	
    	return application;
	}
	
	/**
	 * Load interface for a given plugin which is an Application
	 * @param plugin Runnable plugin (extends from MGSApplication)
	 */
	private void loadPluginInterfaces(RunnablePlugin plugin) {

		ZipInputStream jarFile = null;
		try {
			URL pluginFile = new URL(new URL("file:"), this.pluginsPath + "/" + plugin.getName());
			jarFile = new ZipInputStream(pluginFile.openStream());			
		} 
		catch (MalformedURLException e) {
			System.err.println("Cannot access to " + plugin.getName() + " file");
			return;
		} catch (IOException e) {
			System.err.println("Cannot open " + plugin.getName() + " file");
			return;
		}
		
		ZipEntry currentFile = null;
		Set<String> interfaces = new HashSet<String>();
		String interfacesPackage = plugin.getMainClass().replaceFirst("\\.[^\\.]*$", "") + ".interfaces.";

	    try {
			while((currentFile = jarFile.getNextEntry()) != null ) {				
			    String fileName = currentFile.getName().replaceAll("/", ".");

			    if(fileName.startsWith(interfacesPackage)){
			    	String[] classSplit = fileName.split(".interfaces.");
    
			    	if(classSplit.length == 2){
			    		String className = classSplit[1];
			    		
			    		if(className.endsWith(".class")){
			    			interfaces.add(fileName.replaceFirst("\\.class$", ""));
			    		}
			    	}
			    }
			}
		} catch (IOException e) {
			System.out.println("Cannot browse " + plugin.getName() + " file");
		}
	    
	    plugin.setCategories(interfaces);
	}

	/**
	 * Returns instance of the given plugin main class. Exceptions ensures that
	 * the given object is a subtype of the plugin category
	 * @param plugin plugin to load main class from
	 * @return instance of the plugin main class
	 * @throws IOException report to the exception message
	 */
	public Object loadPlugin(Plugin plugin) throws IOException{
		Class<?> mainClass, implementedClass;
    	Object objet = null;

		try {
			mainClass = this.loader.loadClass(plugin.getMainClass());
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

	   	if(!implementedClass.isAssignableFrom(mainClass)){
    		throw new IOException("Cannot load " + plugin.getMainClass() + " mainClass for " + plugin.getName() + " plugin because it does not implements given category interface " + implementedClass.getName());
    	}
		
		return objet;
	}
	
	/**
	 * Enable to load a plugin without knowing is configuration
	 * @param pluginName
	 * @return An instance of the plugin
	 * @throws IOException Can't load the plugin and get an instance
	 * TODO Check existance plugin
	 */
	public Object loadPlugin(String pluginName) throws IOException{
		Plugin plugin = null; 
		
		try {
			plugin = this.loadPluginConfiguration(pluginName);
		}
		catch(IOException e){
			throw new IOException("Plugin " + pluginName + " does not exists");
		}
		
		return this.loadPlugin(plugin);
	}
	
	/**
	 * Returns runnable plugins list
	 * @return runnable plugins list
	 */
	public Collection<RunnablePlugin> getRunnablePluginsList(){
		return this.runnablePlugins.values();
	}

	/**
	 * Returns all classic plugins.
	 * @return all classic plugins
	 */
	public Collection<Plugin> getClassicPlugins(){
		return this.classicPlugins.values();
	}
	
	/**
	 * Returns all plugins from the given category
	 * @param category category of returned plugins
	 * @return all plugins with the given category
	 */
	public List<Plugin> getClassicPluginsByCategory(String category){
		List<Plugin> list = this.pluginsByCategories.get(category);
		if(list == null){
			list = new ArrayList<Plugin>();
		}
		return list;
	}
}

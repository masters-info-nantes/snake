package fr.univnantes.mgsframework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Framework {
	
	private static String configFilePath = "resources/settings.txt";
	private static Framework framework = null;
	
	public  PluginLoader pluginLoader;
	private String startPluginName;
	private String modifiedStartPluginName;
	private String pluginsPath;
	
	public static Framework getInstance(){
		if(Framework.framework == null){
			Framework.framework = new Framework();
		}
		
		return Framework.framework;
	}
	
	public Framework() {
		this.startPluginName = "";
		this.modifiedStartPluginName = "";
		this.pluginsPath = "";
	}
	
	public void init() throws IOException {
		this.loadConfiguration();
		this.pluginLoader = new PluginLoader();
		this.pluginLoader.loadPluginsList();		
	}
	
	/**
	 * Load framework configuration file located
	 * at snake/ressources/settings.txt
	 * @throws IOException
	 */
	private void loadConfiguration() throws IOException{
		try {
			FileReader configFile = new FileReader(Framework.configFilePath);
			Properties configReader = new Properties();
			configReader.load(configFile);
			
			this.startPluginName = configReader.getProperty("startplugin");
			this.modifiedStartPluginName = this.startPluginName;
			this.pluginsPath = configReader.getProperty("pluginspath");			
		}
		catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new FileNotFoundException("Config file missing at " + Framework.configFilePath);
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Unable to load config file as property file");
		}
		
		System.out.println("Framework configuration");
		System.out.println("* plugins path:  " + this.pluginsPath);
		System.out.println("* start plugin: " + this.startPluginName + "\n");
	}
	
	/**
	 * Run start plugin given in configuration file
	 * with startplugin parameter
	 * @throws IOException
	 */
	public void runStartPlugin() throws IOException{
		MGSApplication startPlugin = this.pluginLoader.loadApplication(this.modifiedStartPluginName);
		startPlugin.run();
	}
	
	public String getStartPluginName(){
		return this.startPluginName;
	}
	
	public String getPluginsPath(){
		return this.pluginsPath;
	}
	/**
	 * Change the start plugin given in platform configuration
	 * @warning Use at your own risks, this override the configuration
	 * @param pluginName
	 */
	public void setStartPlugin(String pluginName){
		this.modifiedStartPluginName = pluginName;
	}
}

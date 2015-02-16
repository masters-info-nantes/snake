package fr.univnantes.mgsframework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Framework {
	
	private static String configFilePath = "ressources/settings.txt";
	public  PluginLoader pluginLoader;
	
	private String startPluginName;
	
	public Framework() throws IOException{
		this.pluginLoader = new PluginLoader();
		this.startPluginName = "";
		
		this.loadConfiguration();
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
			
			this.pluginLoader.setPluginsPath(configReader.getProperty("pluginspath"));
			this.startPluginName = configReader.getProperty("startplugin");			
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
		System.out.println("* plugins path:  " + this.pluginLoader.getPluginsPath());
		System.out.println("* start plugin: " + this.startPluginName + "\n");
	}
	
	/**
	 * Run start plugin given in configuration file
	 * with startplugin parameter
	 * @throws IOException
	 */
	public void runStartPlugin() throws IOException{
		MGSApplication startPlugin = this.pluginLoader.loadApplication(this.startPluginName);
		startPlugin.run();
	}
	
	public String getStartPluginName(){
		return this.startPluginName;
	}
}

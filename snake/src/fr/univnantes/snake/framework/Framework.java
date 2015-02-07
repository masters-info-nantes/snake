package fr.univnantes.snake.framework;

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
		FileReader configFile;
		try {
			configFile = new FileReader(Framework.configFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException("Config file missing at " + Framework.configFilePath);
		} 
		
		Properties configReader = new Properties();
		try {
			configReader.load(configFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Unable to load config file as property file");
		}
		
		this.pluginLoader.setPluginsPath(configReader.getProperty("pluginspath"));
		this.startPluginName = configReader.getProperty("startplugin");
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
}

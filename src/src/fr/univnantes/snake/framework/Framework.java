package fr.univnantes.snake.framework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Framework {
	
	private static String configFilePath = "ressources/settings.txt";
	private PluginLoader pluginLoader;
	
	public Framework(){
		this.pluginLoader = new PluginLoader();
	}
	
	public void loadConfiguration() throws IOException{
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
		
		pluginLoader.setPluginsPath(configReader.getProperty("pluginspath"));
	}
	
	public Set<String> listPlugins(){
		return this.pluginLoader.listPlugins();
	}
}

package runnableplugin; // Package given in pom.xml file

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.AppContext;
import fr.univnantes.mgsframework.Plugin;

// A Runnabe plugin have to implement MGSApplication interface
public class RunnablePlugin implements MGSApplication {

	@Override
	public void run(AppContext app) {
		
		// Get the current plugin, here it is runnableplugin-version.jar
		System.out.println("\n" + app.getCurrentPlugin());
		
		// List all categories provided by this plugin
		System.out.println("List of categories:");
		Set<String> categories = app.getPluginsLoader().getMainPluginCategories();
		for (String string : categories) {
			System.out.println(string);
		}		
		
		// List all available plugins
		System.out.println("\nList of available plugins:");		
		Collection<Plugin> pluginList = app.getPluginsLoader().getClassicPlugins();

		for (Plugin plugin : pluginList) {
			System.out.println(plugin);
			
			// Load each available plugin and call a method
			try {
				SecondaryPlugin second = (SecondaryPlugin) app.getPluginsLoader().loadPlugin(plugin);
				second.method();
			} 
			catch (IOException e) {
				System.err.println("Cannot load first " + plugin.getName() + " plugin");
			}
		}		
	}
}

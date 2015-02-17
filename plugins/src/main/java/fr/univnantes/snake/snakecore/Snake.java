package fr.univnantes.snake.snakecore;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.AppContext;
import fr.univnantes.mgsframework.Plugin;

import fr.univnantes.snake.snakecore.interfaces.Display;

public class Snake implements MGSApplication{

	@Override
	public void run(AppContext app) {
		
		System.out.println("-> Snake plugin");
		System.out.println("\n" + app.getCurrentPlugin());
		
		System.out.println("List of categories:");
		Set<String> categories = app.getPluginsLoader().getMainPluginCategories();
		for (String string : categories) {
			System.out.println(string);
		}		
		
		System.out.println("\nList of available plugins:");		
		Collection<Plugin> pluginList = app.getPluginsLoader().getClassicPlugins();

		for (Plugin plugin : pluginList) {
			System.out.println(plugin);
			
			try {
				Display display = (Display) app.getPluginsLoader().loadPlugin(plugin);
				System.out.print("sayHello: ");
				display.sayHello();
			} 
			catch (IOException e) {
				System.err.println("Cannot load first " + plugin.getName() + " plugin");
			}
			
			System.out.println();
		}		
		
		System.out.println("\n-> Snake end");
	}
}

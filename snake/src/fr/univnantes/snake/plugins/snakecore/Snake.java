package fr.univnantes.snake.plugins.snakecore;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.snake.framework.MGSApplication;
import fr.univnantes.snake.framework.Plugin;
import fr.univnantes.snake.plugins.snakecore.interfaces.Display;

public class Snake extends MGSApplication{

	@Override
	public void run() {
		System.out.println("-> Snake plugin");
		System.out.println("\n" + this.currentPlugin);
		
		System.out.println("List of categories:");
		Set<String> categories = this.pluginsLoader.getMainPluginCategories();
		for (String string : categories) {
			System.out.println(string);
		}		
		
		System.out.println("\nList of available plugins:");		
		Collection<Plugin> pluginList = this.pluginsLoader.getClassicPlugins();

		for (Plugin plugin : pluginList) {
			System.out.println(plugin);
			
			try {
				Display display = (Display) this.pluginsLoader.loadPlugin(plugin);
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

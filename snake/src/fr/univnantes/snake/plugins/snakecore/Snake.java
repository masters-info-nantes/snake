package fr.univnantes.snake.plugins.snakecore;

import java.util.Collection;
import java.util.Set;

import fr.univnantes.snake.framework.MGSApplication;
import fr.univnantes.snake.framework.Plugin;

public class Snake extends MGSApplication{

	@Override
	public void run() {
		System.out.println("-> Snake plugin\n");
		
		System.out.println("List of categories:");
		Set<String> categories = this.pluginsLoader.getMainPluginCategories();
		for (String string : categories) {
			System.out.println(string);
		}
		
		System.out.println("\nList of available plugins:");		
		Collection<Plugin> pluginList = this.pluginsLoader.getClassicPlugins();

		for (Plugin plugin : pluginList) {
			System.out.println(plugin);
		}		
		
		System.out.println("\n-> Snake end");
	}
}

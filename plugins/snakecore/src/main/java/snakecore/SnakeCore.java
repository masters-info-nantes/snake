package snakecore;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;

import snakecore.interfaces.Display; 

public class SnakeCore extends MGSApplication{

	@Override
	public void run() {		
		System.out.println("-> Snake plugin");

		Game game = new Game(this.pluginsLoader);
		game.load();
		game.start();

		System.out.println("\n-> Snake end");
	}

	public void frameworkUsageExample(){
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
	}
}
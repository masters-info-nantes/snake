package fr.univnantes.snake.plugins.snakecore;

import java.util.Set;
import fr.univnantes.snake.framework.MGSApplication;

public class Snake extends MGSApplication{

	@Override
	public void run() {
		System.out.println("-> Snake plugin\n");
		System.out.println("List of available plugins:");
		
		Set<String> pluginList = this.pluginsManager.givePluginsList();

		for (String string : pluginList) {
			System.out.println("* " + string);
		}
	}
}

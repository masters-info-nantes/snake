package fr.univnantes.snake.framework;

import java.io.IOException;
import java.util.Set;

public class App {
	public static void main(String[] args) {
		Framework framework = new Framework();
		
		try {
			framework.loadConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to load framework configuration");
		}
		
		System.out.println("List of availables plugins:");
		Set<String> pluginList = framework.listPlugins();
		for (String pluginName : pluginList) {
			System.out.println("* " + pluginName);
		}
	}
}

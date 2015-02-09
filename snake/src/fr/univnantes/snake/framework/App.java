package fr.univnantes.snake.framework;

import java.io.IOException;
import java.util.Set;

public class App {
	public static void main(String[] args) {
		Framework framework = null;
		
		System.out.println("Framework starts\n----------------");
		try {
			framework = new Framework();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to load framework configuration");
			System.exit(0);
		}
		
		System.out.println("\nRun " + framework.getStartPluginName() + " as main plugin\n----------------\n");
		try {
			framework.runStartPlugin();
		} catch (IOException e) {
			System.err.println("Cannot run start plugin");
			e.printStackTrace();
		}
		
		System.out.println("\nFramework ends");
	}
}

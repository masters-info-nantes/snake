package fr.univnantes.mgsframework;

import java.io.IOException;

public class App {
	public static void main(String[] args) {

		System.out.println("Framework starts\n----------------");
		
		Framework framework = Framework.getInstance();
		try {
			framework.init();
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to load framework configuration");
			System.exit(0);
		}			
		
		System.out.println("\nRun " + framework.getStartPluginName() + " as main plugin\n----------------\n");
		framework.setStartPlugin("platforminspector-0.1.jar");
		try {
			framework.runStartPlugin();
		} 
		catch (IOException e) {
			System.err.println("Cannot run start plugin");
			e.printStackTrace();
		}
		
		System.out.println("\nFramework ends");
	}
}

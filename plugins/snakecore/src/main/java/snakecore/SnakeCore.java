package snakecore;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import javax.swing.JOptionPane;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;

import snakecore.interfaces.Display; 
import java.util.*;
import snakecore.menu.*;

public class SnakeCore extends MGSApplication{

	HashMap pluginsSelected;

	@Override
	public void run() {		
		System.out.println("-> Snake plugin");

		Menu menu = new Menu(this);

	}

	public Model runConfig(){
		Model model = new Model(this.pluginsLoader,this.currentPlugin);
		return model;	
	}

	public void runSnake(HashMap plugins) {
		this.pluginsSelected = plugins;
		Runnable r = new Runnable() {
		public void run() {
			Game game = new Game(pluginsLoader);
			game.load(pluginsSelected);
			game.start();
		    }
		};
		
		Thread myThread = new Thread(r);
		myThread.setDaemon(true);
		myThread.start();


		System.out.println("\n-> Snake end");
	}
	

	public void frameworkUsageExample(){
		System.out.println("\n" + this.currentPlugin);
		/*
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
			
		}	*/			
	}
}

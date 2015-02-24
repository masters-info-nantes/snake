package snakecore;

import snakecore.enums.Direction;
import snakecore.interfaces.Display;
import snakecore.interfaces.Map;
import snakecore.mocks.DisplayMock;
import snakecore.mocks.MapMock;
import fr.univnantes.mgsframework.PluginLoader;

public class Game {
	
	private PluginLoader pluginLoader;
	
	private Display display;
	private Map map;
	private Snake snake;

	private int turn;

	public Game(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
		this.snake = new Snake();
		this.turn = 1;
	}

	public void load(){		
		//this.map = this.pluginLoader.loadPlugin("map-0.1.jar");
		//this.display = this.pluginLoader.loadPlugin("display-0.1.jar");
		
		this.map = new MapMock();
		this.display = new DisplayMock();
		
		this.display.setMap(this.map);
		this.map.addElement(this.snake);
	}

	public void start(){
		this.display.show();
		
		while(this.turn < 10){ // DEBUG reasons
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				System.err.println("Game loop was interrupted with no reasons");
				e.printStackTrace();
			}
			
			this.nextTurn();
		}
	}

	public void nextTurn(){
		this.map.moveElement(this.snake);
		System.out.println("> Next turn: " + turn);
		
		if(turn == 5){
			this.snake.setDirection(Direction.BOTTOM);
		}
		this.snake.evolve();
		
		this.display.updateMap();	
		System.out.println("\n");
		
		this.turn++;
	}
}
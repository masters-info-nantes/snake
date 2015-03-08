package snakecore;

import snakecore.enums.Direction;
import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Display;
import snakecore.interfaces.GameOver;
import snakecore.interfaces.Map;
import snakecore.interfaces.Controller;
import fr.univnantes.mgsframework.PluginLoader;
import java.io.IOException;

public class Game {
	
	private PluginLoader pluginLoader;
	
	private Display display;
	private Map map;
	private GameOver gameOver;
	private Controller controller;
	
	private Snake snake;

	private int turn;

	public Game(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
		this.snake = new Snake(15,15);
		for(int i=0;i<5;i++){
		snake.evolve();
		}
		this.turn = 1;
	}

	public void load(){	
		try{	

		this.map = (Map)this.pluginLoader.loadPlugin("mapGUI-0.1.jar");
		this.display = (Display)this.pluginLoader.loadPlugin("displayGUI-0.1.jar");
		this.gameOver = (GameOver)this.pluginLoader.loadPlugin("gameovertest-0.1.jar");
		this.controller = (Controller)this.pluginLoader.loadPlugin("controllerArrow-0.1.jar");

		}
		catch(IOException e){}		
		
		this.display.setController(this.controller);
		this.display.setMap(this.map);
		this.map.addElement(this.snake);
	}

	public void start(){
		this.display.show();

		while(!this.gameOver.isGameOver()){ 
			this.nextTurn();
			
			try {
				Thread.sleep(200);
			} 
			catch (InterruptedException e) {
				System.err.println("Game loop was interrupted with no reasons");
				e.printStackTrace();
			}		
		}
		this.display.updateMap();		
	}

	public void nextTurn(){
		this.snake.setDirection(controller.getLastDirection());	

		this.snake.move();

		try {
			this.map.moveElement(this.snake, this.snake.getX(), this.snake.getY());
		
			//System.out.println("> Next turn: " + turn);
			
			this.display.updateMap();	
			System.out.println("\n");
		} 
		catch (OutOfMapException e) {
			this.display.setGameOver(true);
			this.gameOver.onSnakeOutOfMap();
		} 
		catch (CollisionException e) {
			this.display.setGameOver(true);
			this.gameOver.onSnakeCollision(e.getElementB());
		}
		
		//this.turn++;
		//this.gameOver.onNextTurn(this.turn);
	}
}

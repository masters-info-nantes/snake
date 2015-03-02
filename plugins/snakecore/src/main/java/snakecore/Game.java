package snakecore;

import snakecore.enums.Direction;
import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Display;
import snakecore.interfaces.GameOver;
import snakecore.interfaces.Map;
import snakecore.mocks.DisplayMock;
import snakecore.mocks.GameOverMock;
import snakecore.mocks.MapMock;
import fr.univnantes.mgsframework.PluginLoader;
import java.io.IOException;

public class Game {
	
	private PluginLoader pluginLoader;
	
	private Display display;
	private Map map;
	private GameOver gameOver;
	
	private Snake snake;

	private int turn;

	public Game(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
		this.snake = new Snake(15,15);
		snake.evolve();
		snake.evolve();
		this.turn = 1;
	}

	public void load(){	
		try{	
		this.map = (Map)this.pluginLoader.loadPlugin("mapGUI-0.1.jar");
		this.display = (Display)this.pluginLoader.loadPlugin("displayGUI-0.1.jar");
		this.gameOver = (GameOver)this.pluginLoader.loadPlugin("gameovermock-0.1.jar");
		}
		catch(IOException e){}		

		//this.map = new MapMock();
		//this.display = new DisplayMock();
		//this.gameOver = new GameOverMock();
		
		this.display.setMap(this.map);
		this.map.addElement(this.snake);
	}

	public void start(){
		this.display.show();
		
		while(!this.gameOver.isGameOver()){ 
			this.nextTurn();
			
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				System.err.println("Game loop was interrupted with no reasons");
				e.printStackTrace();
			}		
		}
	}

	public void nextTurn(){
		this.snake.move();
		
		try {
			this.map.moveElement(this.snake, this.snake.getX(), this.snake.getY());
		} 
		catch (OutOfMapException e) {
			this.gameOver.onSnakeOutOfMap();
		} 
		catch (CollisionException e) {
			this.gameOver.onSnakeCollision(e.getElementB());
		}
		
		System.out.println("> Next turn: " + turn);
		
		if(turn == 5){
			this.snake.setDirection(Direction.BOTTOM);
		}
		//this.snake.evolve();
		
		this.display.updateMap();	
		System.out.println("\n");
		
		this.turn++;
		this.gameOver.onNextTurn(this.turn);
	}
}

package snakecore;

import snakecore.enums.Direction;
import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Display;
import snakecore.interfaces.GameOver;
import snakecore.interfaces.Map;
import snakecore.interfaces.Controller;
import snakecore.interfaces.Score;
import fr.univnantes.mgsframework.PluginLoader;
import java.io.IOException;

import java.util.*;

public class Game {
	
	private PluginLoader pluginLoader;
	
	private Display display;
	private Map map;
	private GameOver gameOver;
	private Controller controller;
	private Score score;
	
	private Snake snake;
	private Frog frog;

	private int turn;
	private int timeToSleep;

	public Game(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
		this.snake = new Snake(15,15);
		for(int i=0;i<5;i++){
			snake.evolve();
		}
		this.turn = 1;
		timeToSleep = 200;
	}

	public void load(HashMap<String,ArrayList<String>> pluginsSelected){	
		try{	

		ArrayList<String> maps = pluginsSelected.get("Map");
		for(String map : maps)
		{
			this.map = (Map)this.pluginLoader.loadPlugin(map);
		}
		ArrayList<String> displays = pluginsSelected.get("Display");
		for(String display : displays)
		{
			this.display = (Display)this.pluginLoader.loadPlugin(display);
		}
		ArrayList<String> overs = pluginsSelected.get("GameOver");
		for(String over : overs)
		{
			this.gameOver = (GameOver)this.pluginLoader.loadPlugin(over);
		}
		ArrayList<String> controls = pluginsSelected.get("Controller");
		for(String control : controls)
		{
			this.controller = (Controller)this.pluginLoader.loadPlugin(control);
		}
		ArrayList<String> scores = pluginsSelected.get("Score");
		for(String score : scores)
		{
			this.score = (Score)this.pluginLoader.loadPlugin(score);
		}						
		//this.display = (Display)this.pluginLoader.loadPlugin(pluginsSelected.get("display"));
		//this.gameOver = (GameOver)this.pluginLoader.loadPlugin(pluginsSelected.get("gameOver"));
		//this.controller = (Controller)this.pluginLoader.loadPlugin(pluginsSelected.get("controller"));
		//this.score = (Score)this.pluginLoader.loadPlugin(pluginsSelected.get("score"));

		}
		catch(IOException e){}		
		
		this.display.setController(this.controller);
		this.display.setMap(this.map);
		this.display.setScore(this.score);
		this.map.addElement(this.snake);
		this.frog = new Frog(25,25);
		this.map.addElement(this.frog);
		this.score.setSpeed(timeToSleep);
	}

	public void start(){
		this.display.show();

		while(!this.gameOver.isGameOver()){ 
			this.nextTurn();
			
			try {
				Thread.sleep(timeToSleep);
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
			this.score.newTurn();	
			System.out.println("\n");
		} 
		catch (OutOfMapException e) {
			this.display.setGameOver(true);
			this.gameOver.onSnakeOutOfMap();
		} 
		catch (CollisionException e) {

			if(e.getElementB().getName()=="Frog")
			{
				this.snake.evolve();
				this.score.eatFrog();
				this.score.setSpeed(timeToSleep);
				this.frog.newFrog(map.getWidth(),map.getHeight());
				if(this.timeToSleep > 50)
				{
					this.timeToSleep -= 10;
				}
			}
			else{
				this.display.setGameOver(true);
				this.gameOver.onSnakeCollision(e.getElementB());
			}
		}
		
		//this.turn++;
		//this.gameOver.onNextTurn(this.turn);
	}
}

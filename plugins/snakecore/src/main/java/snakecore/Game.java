package snakecore;

import snakecore.enums.Direction;
import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Display;
import snakecore.interfaces.GameOver;
import snakecore.interfaces.Map;
import snakecore.interfaces.Controller;
import snakecore.interfaces.Score;
import snakecore.interfaces.SnakeEvent;
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
	private ArrayList<SnakeEvent> snakeEvent;
	
	private Data data;

	public Game(PluginLoader pluginLoader){
		this.pluginLoader = pluginLoader;
		data = new Data();
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
		ArrayList<String> events = pluginsSelected.get("SnakeEvent");
		snakeEvent = new ArrayList<SnakeEvent>();
		for(String event : events)
		{
			snakeEvent.add( (SnakeEvent)this.pluginLoader.loadPlugin(event) );
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
		this.map.addElement(data.snake);
		data.frog = new Frog(25,25);
		this.map.addElement(data.frog);
		this.score.setSpeed(data.timeToSleep);
	}

	public void start(){
		this.display.show();

		if(!snakeEvent.isEmpty()){
			for(SnakeEvent event : snakeEvent)
			{
				event.onStart(data);
			}				
		}

		while(!this.gameOver.isGameOver()){ 
			this.nextTurn();
			
			try {
				Thread.sleep(data.timeToSleep);
			} 
			catch (InterruptedException e) {
				System.err.println("Game loop was interrupted with no reasons");
				e.printStackTrace();
			}		
		}
		this.display.updateMap();		
	}

	public void nextTurn(){
		data.snake.setDirection(controller.getLastDirection());	
		data.snake.move();

		if(!snakeEvent.isEmpty()){
			for(SnakeEvent event : snakeEvent)
			{
				event.onNextTurn(data);
			}				
		}		

		try {
			this.map.moveElement(data.snake, data.snake.getX(), data.snake.getY());
		
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
				data.snake.evolve();
				this.score.eatFrog();
				this.score.setSpeed(data.timeToSleep);
				data.frog.newFrog(map.getWidth(),map.getHeight());
				if(data.timeToSleep > data.sleepLimit)
				{
					data.timeToSleep += data.sleepGain;
				}

				if(!snakeEvent.isEmpty()){
					for(SnakeEvent event : snakeEvent)
					{
						event.onEatFrog(data);
					}				
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

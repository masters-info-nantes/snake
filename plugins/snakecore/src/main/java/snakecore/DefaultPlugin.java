package snakecore;

import fr.univnantes.mgsframework.Plugin;
import snakecore.interfaces.Display;
import snakecore.interfaces.GameOver;
import snakecore.interfaces.Map;
import snakecore.interfaces.Controller;
import snakecore.interfaces.Score;
import java.util.*;

class DefaultPlugin 
{
	private String display;
	private String map;
	private String gameOver;
	private String controller;
	private String score;

	public DefaultPlugin(){
		this.map = "mapGUI-0.1.jar";
		this.display = "displayGUI-0.1.jar";
		this.gameOver = "gameovertest-0.1.jar";
		this.controller = "controllerArrow-0.1.jar";
		this.score = "scoreBasic-0.1.jar";		
	}

	public String getDisplay(){
		return display;
	}

	public int getNbDisplay(){
		return 1;
	}

	public String getMap(){
		return map;
	}

	public int getNbMap(){
		return 1;
	}

	public String getGameOver(){
		return gameOver;
	}

	public int getNbGameOver(){
		return 1;
	}

	public String getController(){
		return controller;
	}

	public int getNbController(){
		return 1;
	}

	public String getScore(){
		return score;
	}	

	public int getNbScore(){
		return 1;
	}

	public HashMap<String,ArrayList<String>> getDefaultPlugins(){
		HashMap res = new HashMap();

		ArrayList<String> displays = new ArrayList();
		displays.add(getDisplay());
		res.put("Display",displays);

		ArrayList<String> maps = new ArrayList();
		maps.add(getMap());		
		res.put("Map",maps);

		ArrayList<String> overs = new ArrayList();
		overs.add(getGameOver());		
		res.put("GameOver",overs);

		ArrayList<String> controls = new ArrayList();
		controls.add(getController());		
		res.put("Controller",controls);

		ArrayList<String> scores = new ArrayList();
		scores.add(getScore());		
		res.put("Score",scores);


		return res;
	}		
}
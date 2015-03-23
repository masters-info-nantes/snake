package snakecore.interfaces;

import snakecore.interfaces.Score;
import java.util.*;

public interface Display 
{
	public void sayHello();
	
	public void show();
	public void setMap(Map map);
	public void setController(ArrayList<Controller> controller);
	public void updateMap();
	public void setGameOver(boolean gameOver);
	public void setScore(Score score);	
}

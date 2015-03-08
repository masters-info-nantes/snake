package snakecore.interfaces;

public interface Display 
{
	public void sayHello();
	
	public void show();
	public void setMap(Map map);
	public void setController(Controller controller);
	public void updateMap();
	public void setGameOver(boolean gameOver);
		
}

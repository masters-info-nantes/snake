package snakecore.interfaces;

public interface GameOver
{
	public void onSnakeOutOfMap();
	public void onNextTurn(int turn);
	public void onSnakeCollision(MapElement element);
	
	public boolean isGameOver();
}
package snakecore.interfaces;

import snakecore.Data;

public interface SnakeEvent
{
	public void onStart(Data data);
	public void onNextTurn(Data data);
	public void onEatFrog(Data data);

}
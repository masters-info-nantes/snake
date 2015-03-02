package gameovertest;

import snakecore.interfaces.GameOver;
import snakecore.interfaces.MapElement;

public class GameOverTest implements GameOver {

	private boolean ended;
	
	public GameOverTest(){
		this.ended = false;
	}
	
	@Override
	public void onSnakeOutOfMap() {
		System.err.println("The snake is out of map. Game continue.");
		this.ended = true;
	}

	@Override
	public void onNextTurn(int turn) {
		/* 
		 * CAUTION: if ended is allready set to true, don't 
		 * set it to false with another method...
		 */
		this.ended |= turn > 10; // As a result: or operator
	}

	@Override
	public void onSnakeCollision(MapElement element) {
		System.out.println("Snake collided with " + element);
	}

	@Override
	public boolean isGameOver() {
		return this.ended;
	}
}

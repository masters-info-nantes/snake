package bigSnakeEvent;

import snakecore.interfaces.SnakeEvent;
import snakecore.interfaces.MapElement;
import snakecore.Data;
import snakecore.Snake;

public class BigSnakeEvent implements SnakeEvent {

	public BigSnakeEvent(){
		
	}

	public void onStart(Data data){
		data.snake.evolve();
		data.snake.evolve();
		data.snake.evolve();
	}

	public void onNextTurn(Data data){

	}

	public void onEatFrog(Data data){
		data.snake.evolve();
		data.snake.evolve();
	}

}

package speedEvent;

import snakecore.interfaces.SnakeEvent;
import snakecore.interfaces.MapElement;
import snakecore.Data;

public class SpeedEvent implements SnakeEvent {

	public SpeedEvent(){
		
	}

	public void onStart(Data data){
		data.timeToSleep = 100;
		data.sleepLimit = 20;
		data.sleepGain = -20;
	}

	public void onNextTurn(Data data){

	}

	public void onEatFrog(Data data){

	}

}

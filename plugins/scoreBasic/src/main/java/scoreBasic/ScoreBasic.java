package scoreBasic;

import snakecore.interfaces.Score;
import snakecore.interfaces.MapElement;

public class ScoreBasic implements Score {

	private int score;
	private int speed;
	
	public ScoreBasic(){
		this.score = 0;
	}
	
	@Override
	public int getScore(){
		return this.score;
	}

	@Override
	public void setSpeed(int speed){
		this.speed = speed;
	}

	@Override
	public void eatFrog(){
		this.score += (201-speed)*10;
	}

	@Override
	public void newTurn(){
		this.score += 1;
	}

}

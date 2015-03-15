package snakecore;

import java.util.*;

public class Data
{

	public Snake snake;
	public Frog frog;

	public int turn;
	public int timeToSleep;
	public int sleepLimit;
	public int sleepGain;

	public Data(){
		this.snake = new Snake(15,15);
		for(int i=0;i<5;i++){
			snake.evolve();
		}
		this.turn = 1;
		timeToSleep = 200;
		sleepLimit = 40;
		sleepGain = -5;
	}

}
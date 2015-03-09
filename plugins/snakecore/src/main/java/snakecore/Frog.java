package snakecore;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import snakecore.interfaces.MapElement;

public class Frog extends MapItem {

    private final static Random RND = new Random();	
	
	public Frog(int x,int y){
		super(x,y,"Frog");
	}

	private static int getRandomX(int nb) {
	    return RND.nextInt(nb);
	}

	private static int getRandomY(int nb) {
	    return RND.nextInt(nb);
	}

	public void newFrog(int x, int y) {
	    setXY(getRandomX(x),getRandomY(y));
	}

}
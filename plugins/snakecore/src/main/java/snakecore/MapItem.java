package snakecore;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import snakecore.interfaces.MapElement;

public class MapItem implements MapElement {
	
	protected Point position;
	protected String name;
	
	public MapItem(int x, int y, String name){
		this.position = new Point(x,y);
		this.name = name;
	}
	
	@Override
	public int getX() {
		return (int) this.position.getX();
	}

	@Override
	public int getY() {
		return (int) this.position.getY();
	}

	@Override
	public LinkedList<MapElement> getSubElements() {
		return new LinkedList<MapElement>();
	}

	@Override
	public void setXY(int x, int y) {
		this.position.setLocation(x, y);
	}

	@Override
	public void move() {
	}

	public String getName(){
		return name;
	}
}

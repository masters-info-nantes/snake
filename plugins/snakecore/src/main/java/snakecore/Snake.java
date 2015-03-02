package snakecore;

import java.awt.Point;
import java.util.LinkedList;

import snakecore.enums.Direction;
import snakecore.interfaces.MapElement;

public class Snake extends MapItem {

	private LinkedList<MapItem> corps;
	private Direction direction;
	
	public Snake(int x,int y){
		super(x,y);
		this.corps = new LinkedList<MapItem>();
		this.corps.addFirst(new MapItem(x,y));
		this.direction = Direction.RIGHT;
	}
	
	public void evolve(){
		MapItem tail = (MapItem) this.corps.getLast();
		MapItem newTail = new MapItem(tail.getX() - 1, tail.getY());
		this.corps.addLast(newTail);		
	}
	
	@Override
	public void move(){	
		Point nextPosition = this.nextHeadPosition();
		if(this.corps.isEmpty()){
			this.corps.addFirst(new MapItem((int)nextPosition.getX(), (int)nextPosition.getY()));
		}
		else {
			MapItem tail = (MapItem) this.corps.removeLast();
			tail.setXY((int)nextPosition.getX(), (int)nextPosition.getY());
			
			this.corps.addFirst(tail);		
		}	
		
		this.setXY((int)nextPosition.getX(), (int)nextPosition.getY()); // obviously...		
	}
	
	private Point nextHeadPosition(){
		Point point = new Point(0,0);
		
		switch(this.direction){
			case BOTTOM:
				point = new Point(this.getX(), this.getY() + 1);
			break;
			
			case LEFT:
				point = new Point(this.getX() - 1, this.getY());				
			break;
			
			case RIGHT:
				point = new Point(this.getX() + 1, this.getY());				
			break;
			
			case TOP:
				point = new Point(this.getX(), this.getY() - 1);	
			break;
		}
		
		return point;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
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
		return (LinkedList<MapElement>)((Object)this.corps);
	}
}

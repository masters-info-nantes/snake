package snakecore;

import java.util.LinkedList;

import snakecore.interfaces.MapElement;

public class Snake extends MapItem {

	private LinkedList<MapItem> corps;
	
	public Snake(){
		super(0,0);
		this.corps = new LinkedList<MapItem>();
	}
	
	public void evolve(){
		MapItem tail = (MapItem) this.corps.getLast();
		MapItem newTail = new MapItem(tail.getX() - 1, tail.getY());
		this.corps.addLast(newTail);		
	}
	
	@Override
	public void move(int x, int y){		
		if(this.corps.isEmpty()){
			this.corps.addFirst(new MapItem(this.getX() + 1, this.getY()));
		}
		else {
			MapItem head = (MapItem) this.corps.getFirst();
			MapItem tail = (MapItem) this.corps.removeLast();
			tail.setXY(head.getX() + 1, head.getY());
			
			this.corps.addFirst(tail);		
		}	
		
		this.setXY(x, y); // obviously...		
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

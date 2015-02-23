package snakecore;

import java.util.SortedSet;
import java.util.Set;
import java.util.TreeSet;

import snakecore.interfaces.MapElement;

public class Snake extends MapItem {

	private SortedSet<MapItem> corps;
	
	public Snake(){
		super(0,0);
		this.corps = new TreeSet<MapItem>();
	}
	
	public void evolve(){
		MapItem tail = this.corps.last();
		MapItem newPart = new MapItem(tail.getX(), tail.getY() + 1);
		
		this.corps.add(newPart);
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
	public Set<MapElement> getSubElements() {
		return (Set<MapElement>)((Object)this.corps);
	}
}

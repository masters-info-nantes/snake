package snakecore.interfaces;

import java.util.Set;

import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;

public interface Map
{
	public int getHeight();
	public int getWidth();

	public boolean addElement(MapElement element);
	public void moveElement(MapElement element, int x, int y) 
		throws OutOfMapException, CollisionException;
	
	public Set<MapElement> getElements();
}
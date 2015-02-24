package snakecore.interfaces;

import java.util.Set;

public interface Map
{
	public int getHeight();
	public int getWidth();

	public boolean addElement(MapElement element);
	public boolean moveElement(MapElement element);
	public Set<MapElement> getElements();
}
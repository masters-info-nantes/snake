package snakecore.interfaces;

import java.util.Set;

public interface MapElement
{
	public int getX();
	public int getY();
	public void setXY(int x, int y);

	public Set<MapElement> getSubElements();
}
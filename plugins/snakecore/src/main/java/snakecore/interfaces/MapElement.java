package snakecore.interfaces;

import java.util.LinkedList;

public interface MapElement
{
	public int getX();
	public int getY();
	public void setXY(int x, int y);
	public String getName();

	public void move();
	public LinkedList<MapElement> getSubElements();
}
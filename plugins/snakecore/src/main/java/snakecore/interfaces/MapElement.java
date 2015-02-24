package snakecore.interfaces;

import java.util.LinkedList;

import snakecore.MapItem;

public interface MapElement
{
	public int getX();
	public int getY();
	public void setXY(int x, int y);

	public void move(int x, int y);
	public LinkedList<MapElement> getSubElements();
}
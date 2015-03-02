package mapGUI;

import java.util.LinkedHashSet;
import java.util.Set;

import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Map;
import snakecore.interfaces.MapElement;


public class MapGUI implements Map {

	private Set<MapElement> elts = new LinkedHashSet<MapElement>();
	
	@Override
	public int getHeight() { return 30; }

	@Override
	public int getWidth() { return 30; }

	@Override
	public boolean addElement(MapElement element) {
		this.elts.add(element);
		return true;
	}

	@Override
	public void moveElement(MapElement element, int x, int y) 
			throws OutOfMapException, CollisionException
	{
		if((x > getWidth()) || (x < 0) || (y > getHeight()) || (y < 0))
		{
			throw new OutOfMapException();
		}	
	}

	@Override
	public Set<MapElement> getElements() { return this.elts; }
}

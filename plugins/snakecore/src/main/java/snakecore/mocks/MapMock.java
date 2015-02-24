package snakecore.mocks;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import snakecore.interfaces.Map;
import snakecore.interfaces.MapElement;

public class MapMock implements Map {

	private Set<MapElement> elts = new LinkedHashSet<MapElement>();
	
	@Override
	public int getHeight() { return 10; }

	@Override
	public int getWidth() { return 10; }

	@Override
	public boolean addElement(MapElement element) {
		this.elts.add(element);
		return true;
	}

	@Override
	public boolean moveElement(MapElement element) {
		element.move();
		return true;
	}

	@Override
	public Set<MapElement> getElements() { return this.elts; }
}
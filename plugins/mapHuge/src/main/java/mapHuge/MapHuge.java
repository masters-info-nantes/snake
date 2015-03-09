package mapHuge;

import java.util.LinkedHashSet;
import java.util.Set;

import snakecore.exceptions.CollisionException;
import snakecore.exceptions.OutOfMapException;
import snakecore.interfaces.Map;
import snakecore.interfaces.MapElement;


public class MapHuge implements Map {

	private Set<MapElement> elts = new LinkedHashSet<MapElement>();
	
	@Override
	public int getHeight() { return 50; }

	@Override
	public int getWidth() { return 100; }

	@Override
	public boolean addElement(MapElement element) {
		this.elts.add(element);
		return true;
	}

	@Override
	public void moveElement(MapElement element, int x, int y) 
			throws OutOfMapException, CollisionException
	{
		if((x >= getWidth()) || (x < 0) || (y >= getHeight()) || (y < 0))
		{
			throw new OutOfMapException();
		}	
		for (MapElement elt : elts) {

			if(elt.getName()=="Snake")
			{
				boolean head = false;
				for (MapElement subelt : elt.getSubElements()) {
					if( (x==subelt.getX())&&(y==subelt.getY()) )
					{
						if(head == true){
							throw new CollisionException(element,subelt);
						}
						else{
							head = true;
						}
					}
				}
			}
			if(elt.getName()=="Frog")
			{
				if( (x==elt.getX())&&(y==elt.getY()) )
				{				
					throw new CollisionException(element,elt);
				}
			}

		}		
	}

	@Override
	public Set<MapElement> getElements() { return this.elts; }
}

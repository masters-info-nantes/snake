package displaymock;

import snakecore.interfaces.Display;
import snakecore.interfaces.Map;
import snakecore.interfaces.MapElement;

/*
 * WARNING:
 * 
 * This class is a mock (for test purpose), don't use it 
 * for complex operations. 
 * Create a real plugin and use it instead.
 * 
 */
public class DisplayMock implements Display {

	private Map map;
	
	@Override
	public void sayHello() { System.out.println("Hello"); }

	@Override
	public void show() { System.out.println("Interface launched"); }

	@Override
	public void setMap(Map map) { this.map = map; }

	@Override
	public void updateMap() {
		for (MapElement elt : this.map.getElements()) {
			System.out.print("Element [" + elt.getX() + "," + elt.getY() + "]: ");
			
			if(elt.getSubElements().isEmpty()){
				System.out.println("no sub-elements");
				return;
			}
			
			
			for (MapElement subelt : elt.getSubElements()) {
				System.out.print("(" + subelt.getX() + "," + subelt.getY() + ") ");
			}
		}
	}
}

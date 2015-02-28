package snakecore.exceptions;

import snakecore.interfaces.MapElement;

public class CollisionException extends Exception {
	private MapElement a;
	private MapElement b;
	
	public CollisionException(MapElement a, MapElement b){
		this.a = a;
		this.b = b;
	}
	
	public MapElement getElementA(){
		return this.a;
	}
	
	public MapElement getElementB(){
		return this.b;
	}	
}

package controllerArrow;


import snakecore.interfaces.Controller;
import snakecore.enums.Direction;
import java.awt.event.KeyEvent;

public class ControllerArrow implements Controller {

	Direction direction;

	public ControllerArrow()
	{
		direction = Direction.RIGHT;
	}

	@Override
	public Direction getLastDirection()
	{
		return direction;
	}
		
	@Override
	public void updateEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) { // touche flèche droite
			direction = Direction.RIGHT;
		} else if (event.getKeyCode() == KeyEvent.VK_LEFT) { // touche flèche gauche
			direction = Direction.LEFT;
		} else if (event.getKeyCode() == KeyEvent.VK_UP) { // touche flèche haut
			direction = Direction.TOP;
		} else if (event.getKeyCode() == KeyEvent.VK_DOWN) { // touche flèche bas
			direction = Direction.BOTTOM;
		}
	}	
}

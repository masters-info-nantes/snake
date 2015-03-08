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
			if (direction!=Direction.LEFT)
				direction = Direction.RIGHT;
		} else if (event.getKeyCode() == KeyEvent.VK_LEFT) { // touche flèche gauche
			if (direction!=Direction.RIGHT)
				direction = Direction.LEFT;
		} else if (event.getKeyCode() == KeyEvent.VK_UP) { // touche flèche haut
			if (direction!=Direction.BOTTOM)	
				direction = Direction.TOP;
		} else if (event.getKeyCode() == KeyEvent.VK_DOWN) { // touche flèche bas
			if (direction!=Direction.TOP)	
				direction = Direction.BOTTOM;
		}
	}	
}

package controllerZQSD;


import snakecore.interfaces.Controller;
import snakecore.enums.Direction;
import java.awt.event.KeyEvent;

public class ControllerZQSD implements Controller {

	Direction direction;

	public ControllerZQSD()
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
		if (event.getKeyCode() == KeyEvent.VK_D) { // touche D -> droite
			if (direction!=Direction.LEFT)
				direction = Direction.RIGHT;
		} else if (event.getKeyCode() == KeyEvent.VK_Q) { // touche Q -> flèche gauche
			if (direction!=Direction.RIGHT)
				direction = Direction.LEFT;
		} else if (event.getKeyCode() == KeyEvent.VK_Z) { // touche Z -> flèche haut
			if (direction!=Direction.BOTTOM)	
				direction = Direction.TOP;
		} else if (event.getKeyCode() == KeyEvent.VK_S) { // touche S -> flèche bas
			if (direction!=Direction.TOP)	
				direction = Direction.BOTTOM;
		}
	}	
}

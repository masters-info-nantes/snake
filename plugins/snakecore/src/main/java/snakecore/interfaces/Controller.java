package snakecore.interfaces;

import java.awt.event.KeyEvent;
import snakecore.enums.Direction;

public interface Controller
{
	public Direction getLastDirection();

	public void updateEvent(KeyEvent event);

}
package snakecore.interfaces;

import java.awt.event.KeyEvent;
import snakecore.enums.Direction;
import snakecore.Data;

public interface Controller
{

	public void setData(Data data);
	public void updateEvent(KeyEvent event);

}
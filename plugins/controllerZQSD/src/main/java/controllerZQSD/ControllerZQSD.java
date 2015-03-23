package controllerZQSD;


import snakecore.interfaces.Controller;
import snakecore.enums.Direction;
import java.awt.event.KeyEvent;
import snakecore.Data;

public class ControllerZQSD implements Controller {

	Data data;

	public ControllerZQSD()
	{
		
	}

	public void setData(Data data){
		this.data = data;
	}	

	@Override
	public void updateEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_D) { // touche flèche droite
			if (data.snake.getDirection()!=Direction.LEFT)
				data.snake.setDirection(Direction.RIGHT);
		} else if (event.getKeyCode() == KeyEvent.VK_Q) { // touche flèche gauche
			if (data.snake.getDirection()!=Direction.RIGHT)
				data.snake.setDirection(Direction.LEFT);
		} else if (event.getKeyCode() == KeyEvent.VK_Z) { // touche flèche haut
			if (data.snake.getDirection()!=Direction.BOTTOM)	
				data.snake.setDirection(Direction.TOP);
		} else if (event.getKeyCode() == KeyEvent.VK_S) { // touche flèche bas
			if (data.snake.getDirection()!=Direction.TOP)	
				data.snake.setDirection( Direction.BOTTOM);
		}
	}		
}

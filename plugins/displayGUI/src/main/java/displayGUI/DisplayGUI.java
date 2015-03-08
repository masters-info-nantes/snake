package displayGUI;

import snakecore.interfaces.Display;
import snakecore.interfaces.Map;
import snakecore.interfaces.MapElement;
import snakecore.interfaces.Controller;


import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;


public class DisplayGUI implements Display {

	private Map map;
	private Controller controller;
	private JPanel content;
	private JFrame frame;
	private static int pixelByCase = 15;
	private boolean gameOver;


    public DisplayGUI() {
        /*titre de la fenêtre*/
        gameOver=false;
        frame = new JFrame("Snake");
        /*fermeture de l'application lorsque la fenêtre est fermée*/
        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*pas de redimensionnement possible de la fenêtre*/
        frame.setResizable(false);
        /*créer un conteneur qui affichera le jeu*/
        content = new JPanel() {
            @Override
        	protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// affichage du modèle du jeu
			if(gameOver == false) {
				affichage(g);
			}
			else {
				affichageGameOver(g);
			}

        	}
        };

        content.addKeyListener(new KeyAdapter() {
			@Override
	      	public void keyPressed(KeyEvent e) {
	      		System.out.println(e);
        		controller.updateEvent(e);
  			}
		});	
		frame.setFocusable(false);
		content.setFocusable(true);

    }
	
	@Override
	public void sayHello() { System.out.println("Hello"); }

	@Override
	public void show(){ 

		System.out.println("Interface launched");
		/*dimensionnement de la fenêre "au plus juste" suivant
            la taille des composants qu'elle contient*/
		frame.pack();
        /*centrage sur l'écran*/
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@Override
	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	@Override
	public void setMap(Map map) { 
		this.map = map; 
        /* dimension de ce conteneur */
        content.setPreferredSize(new Dimension(map.getWidth()*pixelByCase, map.getHeight()*pixelByCase));
        /* ajouter le conteneur à la fenêtre*/
        frame.setContentPane(content);
	}

	@Override
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	@Override
	public void updateMap() {
		content.repaint();
	}

	public void affichage(Graphics g) {
		// activer l'anti-aliasing du dessin
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// dessin du serpent
		for (MapElement elt : this.map.getElements()) {
			System.out.print("Element [" + elt.getX() + "," + elt.getY() + "]: ");
			
			if(elt.getSubElements().isEmpty()){
				System.out.println("no sub-elements");
				return;
			}
			
			
			for (MapElement subelt : elt.getSubElements()) {
				System.out.print("(" + subelt.getX() + "," + subelt.getY() + ") ");
				g.fillOval(subelt.getX()*pixelByCase, subelt.getY()*pixelByCase, pixelByCase, pixelByCase);
			}
		}			    
	}

	public void affichageGameOver(Graphics g){
		String s = "Game Over";
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 40));
		g.drawString(s,(map.getWidth()*pixelByCase)/4,(map.getHeight()*pixelByCase)/4);
	}	
}

package snakecore.menu;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.Label;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Color;

import java.util.*;
import snakecore.*;

import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;

public class Menu {

	private SnakeCore snakecore;
	private JPanel content;
	private JFrame frame;
	private final int width = 300;
	private final int height = 300;

	private PluginAdministrator admin;

	public Menu(SnakeCore s){
		snakecore = s;

		admin = new PluginAdministrator();

        /*titre de la fenêtre*/
        frame = new JFrame("SnakeLauncher");
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
			affichage(g);
        	}
        };

        JButton start = new JButton("  Start Game  ");
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				snakecore.runSnake(admin.getPluginsSelected());
			}
		});



        JButton config = new JButton("Configuration");
		config.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Model m = snakecore.runConfig();
				MenuConfig config = new MenuConfig(m,admin);
				config.setVisible( true );
			}
		});

        /*Methode pour faire un espace la plus moche de l'univers ! */
        JButton espace = new JButton("");             
		espace.setOpaque(false);
		espace.setContentAreaFilled(false);
		espace.setBorderPainted(false);

		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.add(Box.createRigidArea(new Dimension(100, 160)));

        content.add(start);
        content.add(espace);
        content.add(config);


        /* dimension de ce conteneur */
        content.setPreferredSize(new Dimension(300, 300));
        /* ajouter le conteneur à la fenêtre*/
        frame.setContentPane(content);

		/*dimensionnement de la fenêre "au plus juste" suivant
            la taille des composants qu'elle contient*/
		frame.pack();
        /*centrage sur l'écran*/
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);        

		content.repaint();
	}

	public void affichage(Graphics g){
		String s = "Snake";
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 40));
		g.setColor(Color.black);	
		g.drawString(s,90,90);
	}


}

package snakecore;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.RunnablePlugin;
import java.util.Collection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class MenuConfig extends JFrame
{
	private	JTabbedPane tabbedPane;

	private JLabel labelPluginInfosName;
	private JLabel labelPluginInfosMainClass;
	private JLabel labelPluginInfosDescription;

	private RunnablePlugin currentPlugin;
	private Model model;	

	private HashMap<String,ArrayList<String>> pluginsSelected;

	public MenuConfig(Model model,HashMap pluginsSelected)
	{
		this.model = model;
		this.pluginsSelected = pluginsSelected;

		// Fenetre 		
		setTitle( "Configuration" );
       	this.setResizable(false);
		setBackground( Color.gray );

        this.setPreferredSize(new Dimension(400, 400));
		/*dimensionnement de la fenêre "au plus juste" suivant
           	 la taille des composants qu'elle contient*/
		this.pack();
        	/*centrage sur l'écran*/
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		/* ----------------Panel Global ---------------*/
		JPanel panelRoot = new JPanel();
		panelRoot.setLayout(new BorderLayout());
		panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelRoot);

		/* ----------------Panel head ---------------*/
		JPanel panelHead = new JPanel();
		panelHead.setLayout(new SpringLayout());
		panelHead.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JButton back = new JButton();
		panelHead.add(back);
		panelHead.add(new JLabel("Choix des plugins du snake"));

		SpringUtilities.makeCompactGrid(panelHead, 1, 2, 6, 6, 6, 6);

		/* ----------------Main panel ---------------*/
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BorderLayout() );

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();

		// Create the tab pages
		for(String s : this.model.getInterfacesCurrentPlugin()){
			String[] splits = s.split("\\.");
			JScrollPane page = createPage(s);
			if(page!=null)
			{
				tabbedPane.addTab( splits[splits.length-1],createPage(s) );
			}
		}		

		mainPanel.add( tabbedPane, BorderLayout.CENTER );

		/* ----------------Panel end ---------------*/
		panelRoot.add(panelHead, BorderLayout.NORTH);
		panelRoot.add(mainPanel, BorderLayout.CENTER);	
	
	}

	public JScrollPane createPage(String category)
	{
		JPanel panelMain = new JPanel();
		JScrollPane scrollPane;

		System.out.println(" valeur : "+category);

		Collection<Plugin> plugins = this.model.getPluginsByCategory(category);

		if(plugins != null)
		{
			System.out.println(" plugin : "+plugins);

			panelMain.setLayout(new GridLayout(plugins.size(), 1));
			//panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));	

			for(int i = 0; i < plugins.size(); i++){
				Plugin p = (Plugin) plugins.toArray()[i];	

				String[] splits = category.split("\\.");
				ArrayList<String> values = pluginsSelected.get(splits[splits.length-1]);

				if(values.contains(p.getName())){
					panelMain.add(createSection(p,true));
				}		
				else{
					panelMain.add(createSection(p,false));
				}				
			}

			scrollPane = new JScrollPane(panelMain);        
	        //SpringUtilities.makeCompactGrid(panelMain, 5, 1, 6, 6, 6, 6);
			return scrollPane;
		}

        return null;
	}

	/* boolean a true pour cocher par default le checkbox */
	public JPanel createSection(Plugin plugin,boolean coche)
	{
		JPanel panelPlugin = new JPanel();
		panelPlugin.setLayout(new BorderLayout());
		panelPlugin.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		
		JPanel panelPluginInfoText = new JPanel();
		panelPluginInfoText.setLayout(new SpringLayout());		;
		
		JPanel panelButton = new JPanel();
		panelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
		
		final JCheckBox check = new JCheckBox("");
		if(coche == true) {	
			check.setSelected(true);
		}

        panelButton.add(check);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("Checked? " + check.isSelected());
			}
		});        
        
		panelPluginInfoText.add(new JLabel("Name: "));
		panelPluginInfoText.add(new JLabel(plugin.getName()));

		panelPluginInfoText.add(new JLabel("Description: "));		
		panelPluginInfoText.add(new JLabel(plugin.getDescription()));	
		
		SpringUtilities.makeCompactGrid(panelPluginInfoText, 2, 2, 6, 6, 6, 6);
		
		panelPlugin.add(panelPluginInfoText, BorderLayout.CENTER);
		panelPlugin.add(panelButton, BorderLayout.EAST);

		panelPlugin.setMaximumSize(new Dimension(panelPlugin.getWidth(),20));

		return panelPlugin;
	}

}

package snakecore.menu;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import snakecore.*;

import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.RunnablePlugin;
import java.util.Collection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

class MenuConfig extends JFrame
{
	private	JTabbedPane tabbedPane;

	private JLabel labelPluginInfosName;
	private JLabel labelPluginInfosMainClass;
	private JLabel labelPluginInfosDescription;

	private RunnablePlugin currentPlugin;
	private Model model;	

	private PluginAdministrator admin;

	public MenuConfig(Model model,PluginAdministrator admin)
	{
		this.model = model;
		this.admin = admin;

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
		
		JButton back = new JButton("ok");
		back.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				close();
			}
		});		
		panelHead.add(back);
		panelHead.add(new JLabel("       Choix des plugins du snake"));

		SpringUtilities.makeCompactGrid(panelHead, 1, 2, 6, 6, 6, 6);

		/* ----------------Main panel ---------------*/
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BorderLayout() );

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();

		// Create the tab pages
		for(String s : this.model.getInterfacesCurrentPlugin()){
			String[] splits = s.split("\\.");
			JPanel page = createPage(s);
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

	public void close(){
		this.dispose();
	}

	public JPanel createPage(String category)
	{
		JPanel global = new JPanel();
		JPanel panelMain = new JPanel();
		JScrollPane scrollPane;

		System.out.println(" valeur : "+category);

		Collection<Plugin> plugins = this.model.getPluginsByCategory(category);

		JLabel label = new JLabel();

		if(!plugins.isEmpty())
		{
			System.out.println(" plugin : "+plugins);

			panelMain.setLayout(new GridLayout(plugins.size(), 1));
			//panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));	

			for(int i = 0; i < plugins.size(); i++){
				Plugin p = (Plugin) plugins.toArray()[i];	

				String[] splits = category.split("\\.");
				String nameCategory = splits[splits.length-1];
				ArrayList<String> values = admin.getPluginsSelected().get(nameCategory);

				int valMax = admin.nbPluginMax(nameCategory);
				int valMin = admin.nbPluginMin(nameCategory);

				if(valMax==valMin){
					label = new JLabel("Nombre de plugins à sélectionner : "+valMax);
				}
				else {
					if(valMax == 10){
						label = new JLabel("Nombre de plugins à sélectionner : "+valMin+" à n plugins");
					}
					else{
						label = new JLabel("Nombre de plugins à sélectionner : "+valMin+" à "+valMax+" plugins");
					}
				}

				if(values.contains(p.getName())){
					panelMain.add(createSection(p,nameCategory,true));
				}		
				else{
					panelMain.add(createSection(p,nameCategory,false));
				}
			
			}

			scrollPane = new JScrollPane(panelMain);        
			global = new JPanel();
			global.add(label);
			global.add(scrollPane);
	        SpringUtilities.makeCompactGrid(global, 2, 1, 6, 6, 6, 6);
			return global;
		}

        return null;
	}

	/* boolean a true pour cocher par default le checkbox */
	public JPanel createSection(final Plugin plugin,final String category, boolean coche)
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

		admin.addCheckBox(check,plugin.getName());

        panelButton.add(check);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(" status du bouton : "+check.isSelected());
				if(check.isSelected()==true){
					admin.add(category,check,plugin.getName());
				}
				else{
					admin.remove(category,check,plugin.getName());
				}

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

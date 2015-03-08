package platforminspector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.RunnablePlugin;

public class View extends JFrame implements ListSelectionListener, ItemListener, ActionListener {
	
	private JLabel labelPluginPath;
	private JLabel labelStartPlugin;
	
	private JList listMainPlugins;
	
	private JLabel labelPluginInfosName;
	private JLabel labelPluginInfosMainClass;
	private JLabel labelPluginInfosDescription;
	
	private JComboBox comboPluginInterfaces;
	private JTable tableSecondaryPlugins;
	
	private RunnablePlugin currentPlugin;
	private Collection<RunnablePlugin> runnablePlugins;
	private Model model;
	
	private static int TABLE_LINE_MAX = 7;
	
	public View(Model model){
		
		super("MGS platform inspector");
		
		this.model = model;
		this.runnablePlugins = model.getMainPluginList();
		
		// Interface
		JPanel panelRoot = new JPanel();
		panelRoot.setLayout(new BorderLayout());
		panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelRoot);
		
		// Panel platform		
		JPanel panelPlatform = new JPanel();
		panelPlatform.setLayout(new SpringLayout());
		panelPlatform.setBorder(BorderFactory.createTitledBorder("Platform settings"));
		
		this.labelPluginPath = new JLabel(model.getPluginsPath());
		this.labelStartPlugin = new JLabel(model.getStartPluginName());
		
		panelPlatform.add(new JLabel("Plugins path: "));
		panelPlatform.add(this.labelPluginPath);
		panelPlatform.add(new JLabel("Start plugin: "));
		panelPlatform.add(this.labelStartPlugin);
		
		SpringUtilities.makeCompactGrid(panelPlatform, 2, 2, 6, 6, 6, 6);
		
		// Panel plugins
		JPanel panelPlugins = new JPanel();
		panelPlugins.setLayout(new BorderLayout());
		panelPlugins.setBorder(BorderFactory.createTitledBorder("Main plugins"));	

		this.listMainPlugins = new JList();
		//this.listMainPlugins.setMinimumSize(new Dimension(150, 240));
		this.listMainPlugins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.listMainPlugins.addListSelectionListener(this);
		
		// > Panel plugins Infos
		JPanel panelPluginDetails = new JPanel();
		panelPluginDetails.setLayout(new BorderLayout());
		panelPluginDetails.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));		
		
		JPanel panelPluginInfos = new JPanel();
		panelPluginInfos.setLayout(new BorderLayout());
		panelPluginInfos.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		
		JPanel panelPluginInfoText = new JPanel();
		panelPluginInfoText.setLayout(new SpringLayout());		
		
		this.labelPluginInfosName = new JLabel("myplugin-0.1.jar");
		this.labelPluginInfosMainClass = new JLabel("com.test.MyPlugin");
		this.labelPluginInfosDescription = new JLabel("Description");
		
		JPanel panelButtonStart = new JPanel();
		panelButtonStart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
		
        ImageIcon icon = new ImageIcon("../plugins/platforminspector/src/main/resources/start.png");		
		JButton buttonStartPlugin = new JButton(icon);
		buttonStartPlugin.addActionListener(this);
        panelButtonStart.add(buttonStartPlugin);
        
		panelPluginInfoText.add(new JLabel("Name: "));
		panelPluginInfoText.add(this.labelPluginInfosName);
		panelPluginInfoText.add(new JLabel("Main class: "));		
		panelPluginInfoText.add(this.labelPluginInfosMainClass);
		panelPluginInfoText.add(new JLabel("Description: "));		
		panelPluginInfoText.add(this.labelPluginInfosDescription);	
		
		SpringUtilities.makeCompactGrid(panelPluginInfoText, 3, 2, 6, 6, 6, 6);
		
		panelPluginInfos.add(panelPluginInfoText, BorderLayout.CENTER);
		panelPluginInfos.add(panelButtonStart, BorderLayout.EAST);
		
		// > Panel plugins interfaces
		JPanel panelPluginInterfacesAndSecondary = new JPanel();
		panelPluginInterfacesAndSecondary.setLayout(new BoxLayout(panelPluginInterfacesAndSecondary, BoxLayout.Y_AXIS));
		
		JPanel panelPluginsInterfaces = new JPanel();
		panelPluginsInterfaces.setLayout(new BoxLayout(panelPluginsInterfaces, BoxLayout.X_AXIS));
		panelPluginsInterfaces.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelPluginsInterfaces.setMaximumSize(new Dimension(250, 40));
		
		this.comboPluginInterfaces = new JComboBox<String>();
		this.comboPluginInterfaces.addItemListener(this);
		panelPluginsInterfaces.add(new JLabel("Interfaces: "));
		panelPluginsInterfaces.add(this.comboPluginInterfaces);
		
		// > Panel plugins table		
		this.tableSecondaryPlugins = new JTable(View.TABLE_LINE_MAX, 3);
		this.tableSecondaryPlugins.setEnabled(false);
		this.tableSecondaryPlugins.getColumnModel().getColumn(0).setHeaderValue("Nom");
		this.tableSecondaryPlugins.getColumnModel().getColumn(1).setHeaderValue("Main class");
		this.tableSecondaryPlugins.getColumnModel().getColumn(2).setHeaderValue("Description");
		
		panelPluginInterfacesAndSecondary.add(panelPluginsInterfaces);
		panelPluginInterfacesAndSecondary.add(new JScrollPane(this.tableSecondaryPlugins));
		
		// Panel plugins end
		panelPluginDetails.add(panelPluginInfos, BorderLayout.NORTH);
		panelPluginDetails.add(panelPluginInterfacesAndSecondary, BorderLayout.CENTER);
		
		JPanel panelListPlugin = new JPanel();
		panelListPlugin.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));		
		JScrollPane scollListPlugin = new JScrollPane(this.listMainPlugins);
		panelListPlugin.add(scollListPlugin);
		
		panelPlugins.add(panelListPlugin, BorderLayout.WEST);
		panelPlugins.add(panelPluginDetails, BorderLayout.CENTER);
		
		// End stuff
		panelRoot.add(panelPlatform, BorderLayout.NORTH);
		panelRoot.add(panelPlugins, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(600, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		// Init list
		Collection<String> list = new ArrayList<String>();
		for(RunnablePlugin r: this.runnablePlugins){
			list.add(r.getName());
		}
		
		this.listMainPlugins.setListData(list.toArray());
		this.listMainPlugins.setSelectedIndex(0);		
	}
	
	public void display(){
		this.pack();
		this.setVisible(true);
	}
	
	public void setMainPluginsList(Collection<RunnablePlugin> plugins){
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		for(RunnablePlugin r: this.runnablePlugins){
			if(r.getName().equals(this.listMainPlugins.getSelectedValue())){
				this.currentPlugin = r;
			}
		}
		
		this.labelPluginInfosName.setText(this.currentPlugin.getName());
		this.labelPluginInfosMainClass.setText(this.currentPlugin.getMainClass());
		this.labelPluginInfosDescription.setText(this.currentPlugin.getDescription());
		
		this.comboPluginInterfaces.removeAllItems();
		for(String s : this.currentPlugin.getCategories()){
			String[] splits = s.split("\\.");
			this.comboPluginInterfaces.addItem(splits[splits.length-1]);
		}
	
		if(!this.currentPlugin.getCategories().isEmpty()){
			this.comboPluginInterfaces.setSelectedIndex(0);	
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		JComboBox combo = (JComboBox) e.getSource();
		TableModel tableModel = this.tableSecondaryPlugins.getModel();
		for(int i = 0; i < View.TABLE_LINE_MAX; i++){
			tableModel.setValueAt("" , i, 0);
			tableModel.setValueAt("" , i, 1);
			tableModel.setValueAt("" , i, 2);			
		}		
		this.tableSecondaryPlugins.setModel(tableModel);
		
		if(combo.getSelectedIndex() < 0) return;

		String category = (String) this.currentPlugin.getCategories().toArray()[combo.getSelectedIndex()];
		Collection<Plugin> plugins = this.model.getPluginsByCategory(category);
		if(plugins == null) return;
		
		tableModel = this.tableSecondaryPlugins.getModel();
		for(int i = 0; i < plugins.size(); i++){
			Plugin p = (Plugin) plugins.toArray()[i];
			
			tableModel.setValueAt(p.getName() , i, 0);
			tableModel.setValueAt(p.getMainClass() , i, 1);
			tableModel.setValueAt(p.getDescription() , i, 2);			
		}
		
		this.tableSecondaryPlugins.setModel(tableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Runnable r = new Runnable() {
            public void run() {
        		try {
        			model.runMainPlugin(currentPlugin);
        		} 
        		catch (IOException e1) {
        			JOptionPane.showMessageDialog(null, e1.getMessage());
        		}
            }
        };
        
        Thread myThread = new Thread(r);
        myThread.setDaemon(true);
        myThread.start();
	}
}


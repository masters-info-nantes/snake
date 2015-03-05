package platforminspector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;

public class View extends JFrame {
	
	private JLabel labelPluginPath;
	private JLabel labelStartPlugin;
	
	private JList listMainPlugins;
	
	private JLabel labelPluginInfosName;
	private JLabel labelPluginInfosMainClass;
	private JLabel labelPluginInfosDescription;
	
	private JComboBox comboPluginInterfaces;
	private JTable tableSecondaryPlugins;
	
	public View(){
		
		super("MGS platform inspector");
		
		JPanel panelRoot = new JPanel();
		panelRoot.setLayout(new BorderLayout());
		panelRoot.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelRoot);
		
		// Panel platform		
		JPanel panelPlatform = new JPanel();
		panelPlatform.setLayout(new SpringLayout());
		panelPlatform.setBorder(BorderFactory.createTitledBorder("Platform settings"));
		
		this.labelPluginPath = new JLabel("/path/to/plugins/dir");
		this.labelStartPlugin = new JLabel("startplugin-0.1.jar");
		
		panelPlatform.add(new JLabel("Plugins path: "));
		panelPlatform.add(this.labelPluginPath);
		panelPlatform.add(new JLabel("Start plugin: "));
		panelPlatform.add(this.labelStartPlugin);
		
		SpringUtilities.makeCompactGrid(panelPlatform, 2, 2, 6, 6, 6, 6);
		
		// Panel plugins
		JPanel panelPlugins = new JPanel();
		panelPlugins.setLayout(new BorderLayout());
		panelPlugins.setBorder(BorderFactory.createTitledBorder("Main plugins"));	
		
		String[] choix = {" Pierre", " Paul", " Jacques", " Lou", " Marie"};
		this.listMainPlugins = new JList(choix);
		this.listMainPlugins.setPreferredSize(new Dimension(150, 240));
		this.listMainPlugins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// > Panel plugins Infos
		JPanel panelPluginDetails = new JPanel();
		panelPluginDetails.setLayout(new BorderLayout());
		panelPluginDetails.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));		
		
		JPanel panelPluginInfos = new JPanel();
		panelPluginInfos.setLayout(new SpringLayout());		
		panelPluginInfos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.labelPluginInfosName = new JLabel("myplugin-0.1.jar");
		this.labelPluginInfosMainClass = new JLabel("com.test.MyPlugin");
		this.labelPluginInfosDescription = new JLabel("Description");
		
		panelPluginInfos.add(new JLabel("Name: "));
		panelPluginInfos.add(this.labelPluginInfosName);
		panelPluginInfos.add(new JLabel("Main class: "));		
		panelPluginInfos.add(this.labelPluginInfosMainClass);
		panelPluginInfos.add(new JLabel("Description: "));		
		panelPluginInfos.add(this.labelPluginInfosDescription);
		
		SpringUtilities.makeCompactGrid(panelPluginInfos, 3, 2, 6, 6, 6, 6);
		
		// > Panel plugins interfaces
		JPanel panelPluginInterfacesAndSecondary = new JPanel();
		panelPluginInterfacesAndSecondary.setLayout(new BoxLayout(panelPluginInterfacesAndSecondary, BoxLayout.Y_AXIS));
		
		JPanel panelPluginsInterfaces = new JPanel();
		panelPluginsInterfaces.setLayout(new BoxLayout(panelPluginsInterfaces, BoxLayout.X_AXIS));
		panelPluginsInterfaces.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelPluginsInterfaces.setMaximumSize(new Dimension(250, 40));
		
		this.comboPluginInterfaces = new JComboBox<String>(choix);
		panelPluginsInterfaces.add(new JLabel("Interfaces: "));
		panelPluginsInterfaces.add(this.comboPluginInterfaces);
		
		// > Panel plugins table
		String[] columnNames = { "Name", "mainClass", "Description" };

		Object[][] data = {
				{"toto-0.1.jar", "com.toto.Toto", "Description" }
		};		
		
		this.tableSecondaryPlugins = new JTable(data, columnNames);		
		this.tableSecondaryPlugins.setEnabled(false);
		
		panelPluginInterfacesAndSecondary.add(panelPluginsInterfaces);
		panelPluginInterfacesAndSecondary.add(new JScrollPane(this.tableSecondaryPlugins));
		
		// Panel plugins end
		panelPluginDetails.add(panelPluginInfos, BorderLayout.NORTH);
		panelPluginDetails.add(panelPluginInterfacesAndSecondary, BorderLayout.CENTER);
		
		JPanel panelListPlugin = new JPanel();
		panelListPlugin.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
		panelListPlugin.add(this.listMainPlugins);
		
		panelPlugins.add(panelListPlugin, BorderLayout.WEST);
		panelPlugins.add(panelPluginDetails, BorderLayout.CENTER);
		
		// End stuff
		panelRoot.add(panelPlatform, BorderLayout.NORTH);
		panelRoot.add(panelPlugins, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(600, 400));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void display(){
		this.pack();
		this.setVisible(true);
	}
	
	public void setPluginsPath(String path){
		this.labelPluginPath.setText(path);
	}
	
	public void setStartPlugin(String plugin){
		this.labelStartPlugin.setText(plugin);
	}
}

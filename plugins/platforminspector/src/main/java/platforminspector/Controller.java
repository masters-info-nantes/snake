package platforminspector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import fr.univnantes.mgsframework.Plugin;
import fr.univnantes.mgsframework.RunnablePlugin;

public class Controller implements ListSelectionListener, ItemListener, ActionListener {
	
	private View view;
	private Model model;
	
	private RunnablePlugin currentPlugin;
	private Collection<RunnablePlugin> runnablePlugins;	
	
	public Controller(Model model){
		this.model = model;
		this.runnablePlugins = this.model.getMainPluginList();
	}
	
	public void setView(View view){
		this.view = view;
		
		this.view.setPluginPath(this.model.getPluginsPath());
		this.view.setStartPlugin(this.model.getStartPluginName());
		
		Collection<String> list = new ArrayList<String>();
		for(RunnablePlugin r: this.runnablePlugins){
			list.add(r.getName());
		}
		this.view.setMainPluginList(list);		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		JList list = (JList) e.getSource();
		
		for(RunnablePlugin r: this.runnablePlugins){
			if(r.getName().equals(list.getSelectedValue())){
				this.currentPlugin = r;
			}
		}
		
		this.view.setPluginInfosName(this.currentPlugin.getName());
		this.view.setPluginInfosMainClass(this.currentPlugin.getMainClass());
		this.view.setPluginInfosDescription(this.currentPlugin.getDescription());
		
		Collection<String> categories = new ArrayList<String>();
		for(String s : this.currentPlugin.getCategories()){
			String[] splits = s.split("\\.");
			categories.add(splits[splits.length-1]);
		}
		
		this.view.setInterfacesList(categories);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {		
		JComboBox combo = (JComboBox) e.getSource();

		if(combo.getSelectedIndex() < 0){
			this.view.setSecondaryList(new ArrayList<Plugin>());
			return;
		}
		
		String category = (String) this.currentPlugin.getCategories().toArray()[combo.getSelectedIndex()];
		Collection<Plugin> plugins = this.model.getPluginsByCategory(category);
		
		this.view.setSecondaryList(plugins);
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

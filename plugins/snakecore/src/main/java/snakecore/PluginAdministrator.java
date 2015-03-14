package snakecore;

import java.util.*;
import fr.univnantes.mgsframework.MGSApplication;
import fr.univnantes.mgsframework.Plugin;
import javax.swing.JCheckBox;


public class PluginAdministrator{


	HashMap<String,ArrayList<String>> pluginsSelected; //key = interfaceName, value = list of nameplugins selected 
	HashMap<String,JCheckBox> checkboxList; //key = pluginName , value = checkbox
	DefaultPlugin defaultPlugin = new DefaultPlugin();

	public PluginAdministrator(){
		defaultPlugin = new DefaultPlugin();
		this.pluginsSelected = defaultPlugin.getDefaultPlugins();
		checkboxList = new HashMap();
	}

	public HashMap<String,ArrayList<String>> getPluginsSelected(){
		return pluginsSelected;
	}

	public void addCheckBox(JCheckBox box,String pluginName){
		checkboxList.put(pluginName,box);
	}

	public void add(String category,JCheckBox box,String pluginName){
		
		int nbPluginMax = nbPluginMax(category);

		ArrayList<String> pluginsName = pluginsSelected.get(category);

		if(nbPluginMax <= pluginsName.size()) {
			//on desactive le premier plugin de la liste
			String pluginAlea = pluginsName.get(0);
			//decochage de la checkbox du plugin
			JCheckBox checkbox = checkboxList.get(pluginAlea);
			checkbox.setSelected(false);
			//ajout du plugin selectionné dans la liste des plugins selectionné
			pluginsName.set(0,pluginName);
			pluginsSelected.put(category,pluginsName);
		}
		else {
			//ajout du plugin selectionné dans la liste des plugins selectionné
			pluginsName.add(pluginName);
			pluginsSelected.put(category,pluginsName);
		}
	}

	public void remove(String category,JCheckBox box,String pluginName){

		int nbPluginMin = nbPluginMin(category);

		ArrayList<String> pluginsName = pluginsSelected.get(category);

		if(nbPluginMin >= pluginsName.size()) {
			box.setSelected(true);
		}
		else{
			pluginsName.remove(pluginName);
			pluginsSelected.put(category,pluginsName);
		}
	}

	public int nbPluginMax(String category){
		int nbPluginMax;
		switch(category) {
		  	case "Display":
		    	nbPluginMax = defaultPlugin.getNbDisplayMax();
		    	break;
		  	case "Map":
		    	nbPluginMax = defaultPlugin.getNbMapMax();
		    	break;
		  	case "Controller":
		    	nbPluginMax = defaultPlugin.getNbControllerMax();
		    	break;
		  	case "GameOver":
		    	nbPluginMax = defaultPlugin.getNbGameOverMax();
		    	break;		    	
		  	case "Score":
		    	nbPluginMax = defaultPlugin.getNbScoreMax();
		    	break;
		  	default:
		    	nbPluginMax = 1;
		}
		return nbPluginMax;
	}

	public int nbPluginMin(String category){
		int nbPluginMin;
		switch(category) {
		  	case "Display":
		    	nbPluginMin = defaultPlugin.getNbDisplayMin();
		    	break;
		  	case "Map":
		    	nbPluginMin = defaultPlugin.getNbMapMin();
		    	break;
		  	case "Controller":
		    	nbPluginMin = defaultPlugin.getNbControllerMin();
		    	break;
		  	case "GameOver":
		    	nbPluginMin = defaultPlugin.getNbGameOverMin();
		    	break;		    	
		  	case "Score":
		    	nbPluginMin = defaultPlugin.getNbScoreMin();
		    	break;
		  	default:
		    	nbPluginMin = 1;
		}
		return nbPluginMin;
	}
}
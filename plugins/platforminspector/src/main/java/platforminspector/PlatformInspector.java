package platforminspector;

import fr.univnantes.mgsframework.MGSApplication;

public class PlatformInspector extends MGSApplication {
	
	@Override
	public void run() {
		View view = new View();
		Model model = new Model(this.pluginsLoader);
		
		view.setPluginsPath(model.getPluginsPath());
		view.setStartPlugin(model.getStartPluginName());
		view.setMainPluginsList(model.getMainPluginList());
		
		view.setModel(model);
		view.display();
	}
}

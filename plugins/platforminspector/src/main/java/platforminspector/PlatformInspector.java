package platforminspector;

import fr.univnantes.mgsframework.MGSApplication;

public class PlatformInspector extends MGSApplication {
	
	@Override
	public void run() {
		View view = new View();
		
		view.setPluginsPath(this.pluginsLoader.getPluginsPath());
		view.setStartPlugin(this.pluginsLoader.getStartPlugin());
		
		view.display();
	}
}

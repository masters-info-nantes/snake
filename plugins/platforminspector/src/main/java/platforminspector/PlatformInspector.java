package platforminspector;

import fr.univnantes.mgsframework.MGSApplication;

public class PlatformInspector extends MGSApplication {
	
	@Override
	public void run() {
		Model model = new Model(this.pluginsLoader);
		View view = new View(model);
		view.display();
	}
}

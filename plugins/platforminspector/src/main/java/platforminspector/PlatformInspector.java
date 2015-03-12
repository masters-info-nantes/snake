package platforminspector;

import fr.univnantes.mgsframework.MGSApplication;

public class PlatformInspector extends MGSApplication {
	
	@Override
	public void run() {
		Model model = new Model(this.pluginsLoader);
		Controller controller = new Controller(model);
		View view = new View(controller);
		
		controller.setView(view);
		view.display();
	}
}

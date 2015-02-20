
package secondarypluginone; // Package given in pom.xml file

// The interface defined in runnable plugin
import runnableplugin.interfaces.SecondaryPlugin;

public class RunnablePlugin implements SecondaryPlugin {

	@Override
	public void method(){
		System.out.println("secondarypluginone");
	}
}

package fr.univnantes.mgsframework;

import java.util.HashSet;
import java.util.Set;

public class RunnablePlugin extends Plugin {

	private Set<String> categories;
	
	// Plugins will not be able to instance a new Plugin  	
	protected RunnablePlugin(String name, String category, String mainClass,
			boolean runnable, String description) 
	{
		super(name, category, mainClass, runnable, description);
		this.setCategories(new HashSet<String>());
	}

	public Set<String> getCategories() {
		return categories;
	}

	protected void setCategories(Set<String> categories) {
		this.categories = categories;
	}
}

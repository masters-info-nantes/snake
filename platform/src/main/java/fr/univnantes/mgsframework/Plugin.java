package fr.univnantes.mgsframework;

public class Plugin {
	
	private String name;
	private String category;
	private String mainClass;
	private boolean runnable;
	private String description;
	
	// Plugins will not be able to instance a new Plugin
	protected Plugin(String name, String category, String mainClass, boolean runnable, String description){
		this.name = name;
		this.category = category;
		this.mainClass = mainClass;
		this.runnable = runnable;
		this.description = (description != null && !"".equals(description)) ? description : "-";
	}
	
	public String getName() {
		return this.name;
	}

	public String getCategory() {
		return this.category;
	}
	
	public String getMainClass(){
		return this.mainClass;
	}
	
	public void setMainClass(String mainClass){
		this.mainClass = mainClass;
	}
	
	public boolean isRunnable(){
		return this.runnable;
	}	
	
	public String getDescription(){
		return this.description;
	}
	
	@Override
	public String toString(){
		StringBuilder text = new StringBuilder();
		text.append("[" + this.name + "]\n");
		text.append("runnable: " + (this.runnable ? "yes" : "no") + "\n");
		text.append("mainClass: " + this.mainClass + "\n");
		text.append("category: " + this.category + "\n");
		text.append("description: " + this.description + "\n");
		return text.toString();
	}
}

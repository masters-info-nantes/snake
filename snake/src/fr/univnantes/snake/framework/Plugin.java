package fr.univnantes.snake.framework;

public class Plugin {
	private String name;
	private String category;
	private String mainClass;
	private boolean runnable;
	
	public Plugin(String name, String category, String mainClass, boolean runnable){
		this.name = name;
		this.category = category;
		this.mainClass = mainClass;
		this.runnable = runnable;
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
	
	@Override
	public String toString(){
		StringBuilder text = new StringBuilder();
		text.append("[" + this.name + "]\n");
		text.append("category: " + this.category + "\n");
		text.append("mainClass: " + this.mainClass + "\n");
		text.append("runnable: " + (this.runnable ? "yes" : "no") + "\n");
		return text.toString();
	}
}

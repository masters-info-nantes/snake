package fr.univnantes.snake.framework;

public class Plugin {
	private String name;
	private String category;
	private String mainClass;
	
	public Plugin(String name, String category, String mainClass){
		this.name = name;
		this.category = category;
		this.mainClass = mainClass;
	}
	
	public Plugin(String name, String category){
		this(name, category, "");
	}

	public boolean isRunnable(){
		return "MGSApplication".equals(this.category);
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
	
	@Override
	public String toString(){
		StringBuilder text = new StringBuilder();
		text.append("[" + this.name + "]\n");
		text.append("category: " + this.category + "\n");
		
		if(this.isRunnable()){
			text.append("mainClass: " + this.mainClass + "\n");
		}
		
		return text.toString();
	}
}

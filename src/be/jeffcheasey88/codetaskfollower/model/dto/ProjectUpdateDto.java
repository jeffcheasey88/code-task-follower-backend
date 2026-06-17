package be.jeffcheasey88.codetaskfollower.model.dto;

public class ProjectUpdateDto{
	
	private String name;
	private String color;
	
	public ProjectUpdateDto(String name, String color){
		this.name = name;
		this.color = color;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setColor(String color){
		this.color = color;
	}
	public String getColor(){
		return this.color;
	}

}

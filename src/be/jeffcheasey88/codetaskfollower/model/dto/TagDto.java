package be.jeffcheasey88.codetaskfollower.model.dto;

public class TagDto{
	
	private String name;
	private String color;
	
	public TagDto(String name, String color){
		this.name = name;
		this.color = color;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getColor(){
		return this.color;
	}
}

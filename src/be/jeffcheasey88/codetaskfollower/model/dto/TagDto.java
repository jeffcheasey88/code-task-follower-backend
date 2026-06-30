package be.jeffcheasey88.codetaskfollower.model.dto;

public class TagDto {
	private Integer id;
	private String name;
	private String color;
	
	public TagDto(Integer id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getColor(){
		return this.color;
	}
}

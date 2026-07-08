package be.jeffcheasey88.codetaskfollower.dto;

public class StateDto{
	
	private Integer id;
	private String name;
	private String color;
	
	public StateDto(Integer id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getColor() {
		return this.color;
	}
}

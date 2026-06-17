package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class TagDto{
	private int id;
	private String name;
	private String color;
	public TagDto(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
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

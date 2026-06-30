package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Tag extends Model {
	@Key(auto=true) private int id;
	private String name;
	private String color;
	
	public Tag(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public int getId(){
		return this.id;
	}
	
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = check("name", name);
	}
	
	public String getColor(){
		return this.color;
	}
	public void setColor(String color){
		this.color = check("color", color);
	}
}

package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;
import dev.peerat.mapping.TreasureCache;

@Treasure
public class Tag{

	@Key(auto=true) private int id;
	private String name;
	private String color;
	
	public Tag(int id, String name, String color){
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
	
	public static List<Tag> getTags(){
		return TreasureCache.<Tag>selectAll().toList();
	}
	
	public static Tag getTag(int id){
		return TreasureCache.<Tag>selectAll().filter(tag -> tag.id == id).get();
	}
}

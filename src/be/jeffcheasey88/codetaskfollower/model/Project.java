package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;
import dev.peerat.mapping.TreasureCache;

@Treasure
public class Project{

	@Key(auto=true) private int id;
	private String name;
	private String color;
	private List<State> states;
	private List<Task> tasks;
	private List<Branch> branches;
	
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
	
//	public static Project getProject(int id){
//		return TreasureCache.<Project>selectAll().filter(tag -> tag.id == id).get();
//	}
	
}

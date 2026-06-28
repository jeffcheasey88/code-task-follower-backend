package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Project {

	@Key(auto=true) private int id;
	private String name;
	private String color;
	private List<State> states;
	private List<Task> tasks;
	private List<Branch> branches;
	
	public Project(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public Project(int id, String name, String color, List<State> states, List<Task> tasks, List<Branch> branches){
		this(id, name, color);
		this.states = states;
		this.tasks = tasks;
		this.branches = branches;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

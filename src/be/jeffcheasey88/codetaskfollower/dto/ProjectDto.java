package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public class ProjectDto{
	private int id;
	private String name;
	private String color;
	private List<StateDto> states;
	private List<TaskDto> tasks;
	private List<BranchDto> branches;
	public ProjectDto(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	public ProjectDto(int id, String name, String color, List<StateDto> states, List<TaskDto> tasks, List<BranchDto> branches){
		this(id, name, color);
		this.states = states;
		this.tasks = tasks;
		this.branches = branches;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
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

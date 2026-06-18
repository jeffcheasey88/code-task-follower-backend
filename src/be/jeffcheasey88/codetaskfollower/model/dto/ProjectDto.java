package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class ProjectDto{
	private int id;
	private String name;
	private String color;
	private List<StateDto> states;
	private List<TaskDto> tasks;
	private List<BranchDto> branches;
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

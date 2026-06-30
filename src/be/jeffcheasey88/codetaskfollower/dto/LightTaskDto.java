package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public class LightTaskDto {
	private int id;
	private String name;
	private StateDto state;
	private List<TagDto> tags;
	private List<TaskDto> dependencies;
	
	public LightTaskDto(int id, String name, StateDto state, List<TagDto> tags, List<TaskDto> dependencies) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.tags = tags;
		this.dependencies = dependencies;
	}
	
	public int getId() {
		return id;
	}	
	public String getName() {
		return name;
	}
	public StateDto getState() {
		return state;
	}
}

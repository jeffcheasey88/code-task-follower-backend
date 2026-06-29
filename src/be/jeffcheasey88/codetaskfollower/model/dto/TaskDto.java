package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class TaskDto{
	private int id;
	private String name;
	private StateDto state;
	private ChronometerDto chronometer;
	private List<TagDto> tags;
	private List<TaskDto> dependencies;
	private List<ProjectDto> projects;
	private List<CommitDto> commits;
	private List<BranchDto> branches;
	private List<CodeDto> codes;
	public TaskDto(int id, String name, StateDto state, ChronometerDto chronometer, List<TagDto> tags, List<TaskDto> dependencies, List<ProjectDto> projects, List<CommitDto> commits, List<BranchDto> branches, List<CodeDto> codes){
		this.id = id;
		this.name = name;
		this.state = state;
		this.chronometer = chronometer;
		this.tags = tags;
		this.dependencies = dependencies;
		this.projects = projects;
		this.commits = commits;
		this.branches = branches;
		this.codes = codes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
}

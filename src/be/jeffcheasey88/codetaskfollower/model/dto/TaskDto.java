package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class TaskDto extends LightTaskDto {
	private ChronometerDto chronometer;
	private List<ProjectDto> projects;
	private List<CommitDto> commits;
	private List<BranchDto> branches;
	private List<CodeDto> codes;
	
	public TaskDto(int id, String name, StateDto state, ChronometerDto chronometer, List<TagDto> tags, List<TaskDto> dependencies, List<ProjectDto> projects, List<CommitDto> commits, List<BranchDto> branches, List<CodeDto> codes) {
		super(id, name, state, tags, dependencies);
		
		this.chronometer = chronometer;
		this.projects = projects;
		this.commits = commits;
		this.branches = branches;
		this.codes = codes;
	}
}

package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class TaskDto{
	private int id;
	private String name;
	private StateDto state;
	private List<TagDto> tags;
	private List<TaskDto> dependencies;
	private List<ProjectDto> projects;
	private List<CommitDto> commits;
	private List<CodeDto> codes;
}

package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record ProjectDto (
	int id,
	String name,
	String color,
	String description,
	List<StateDto> states,
	List<TaskDto> tasks,
	List<BranchDto> branches
) {}

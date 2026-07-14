package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record TaskDto (
	Integer id,
	String name,
	String description,
	Integer stateId,
	List<Integer> tags,
	List<TaskDto> dependencies,
	//ChronometerDto chronometer,
	List<ProjectDto> projects,
	List<CommitDto> commits,
	List<BranchDto> branches,
	List<CodeDto> codes
) {}

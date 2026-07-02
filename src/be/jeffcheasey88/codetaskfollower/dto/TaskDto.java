package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record TaskDto (
	int id,
	String name,
	//StateDto state,
	List<TagDto> tags,
	List<TaskDto> dependencies,
	//ChronometerDto chronometer,
	List<ProjectDto> projects,
	List<CommitDto> commits,
	List<BranchDto> branches,
	List<CodeDto> codes
) {}

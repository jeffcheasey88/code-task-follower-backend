package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record LightTaskDto (
	int id,
	String name,
	//StateDto state,
	List<TagDto> tags,
	List<TaskDto> dependencies
) {}
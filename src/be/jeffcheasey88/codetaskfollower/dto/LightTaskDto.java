package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record LightTaskDto (
	Integer id,
	String name,
	//StateDto state,
	List<TagDto> tags,
	List<TaskDto> dependencies
) {}
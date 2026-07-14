package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public record LightTaskDto (
	Integer id,
	String name,
	String description,
	Integer stateId,
	List<TagDto> tags,
	List<TaskDto> dependencies
) {}
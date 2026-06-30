package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.model.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.model.dto.TaskDto;

public class TaskMapper extends Mapper<TaskDto, LightTaskDto, Task> {
	@Override
	public TaskDto toDto(Task entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LightTaskDto toLightDto(Task entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fullCopyDtoToEntity(TaskDto dto, Task entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void safeCopyDtoToEntity(TaskDto dto, Task entity) {
		// TODO Auto-generated method stub
		
	}
}

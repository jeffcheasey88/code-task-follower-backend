package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.framework.dependency.Injection;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;

@Injection
public class TaskMapper extends Mapper<TaskDto, LightTaskDto, Task> {
	@Override
	public TaskDto toDto(Task model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LightTaskDto toLightDto(Task model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fullCopyDtoToModel(TaskDto dto, Task model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void safeCopyDtoToModel(TaskDto dto, Task model) {
		// TODO Auto-generated method stub
		
	}
}

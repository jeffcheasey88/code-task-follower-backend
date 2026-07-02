package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.framework.dependency.Injection;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;

@Injection
public class TaskMapper extends Mapper<TaskDto, LightTaskDto, Task> {
	private TagMapper tagMapper;

	public TaskMapper(@Injection TagMapper stateMapper) {
		this.tagMapper = stateMapper;
	}
	
	@Override
	public TaskDto toDto(Task model) {
		return new TaskDto(
			model.getId(),
			model.getName(),
			//model.getState(),
			tagMapper.toDto(model.getTags()),
			toDto(model.getDependencies()),
			//null,
			null,
			null,
			null,
			null
		);
	}

	@Override
	public LightTaskDto toLightDto(Task model) {
		return new LightTaskDto(
			model.getId(),
			model.getName(),
			//model.getState(),
			tagMapper.toDto(model.getTags()),
			toDto(model.getDependencies())
		);
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

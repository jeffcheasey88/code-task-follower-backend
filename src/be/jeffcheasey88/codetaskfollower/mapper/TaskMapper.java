package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.framework.dependency.Injection;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;

@Injection
public class TaskMapper extends Mapper<TaskDto, LightTaskDto, Task>{
	
	@Injection private TagMapper tagMapper;

	@Override
	public TaskDto toDto(Task model) {
		return new TaskDto(
			model.getId(),
			model.getName(),
			model.getDescription(),
			0,
			null, //model.getState(),
			model.getTags() == null ? null : tagMapper.toDto(model.getTags()).stream().map(t -> t.getId()).toList(),
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
			model.getDescription(),
			null,//model.getState(),
			tagMapper.toDto(model.getTags()),
			toDto(model.getDependencies())
		);
	}

	@Override
	public void fullCopyDtoToModel(TaskDto dto, Task model) {
		model.setName(dto.name());
		model.setDescription(dto.description());
		model.setEstimateSeconds(dto.estimateSeconds());
	}

	@Override
	public void safeCopyDtoToModel(TaskDto dto, Task model) {
		if(dto.name() != null) model.setName(dto.name());
		if(dto.description() != null) model.setDescription(dto.description());
		if(dto.estimateSeconds() != null) model.setEstimateSeconds(dto.estimateSeconds());
	}
}

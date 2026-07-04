package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TaskController{
	
	@Injection private TaskRepository taskRepository;
	@Injection private TaskMapper taskMapper;
	
    @Route(path = "/tasks")
	public List<LightTaskDto> getTasks() {
    	return taskMapper.toLightDto(taskRepository.findAll());
	}
	
	@Route(path = "/tasks/(\\d+)")
	public TaskDto getTask(@Argument Task task) {
		return taskMapper.toDto(task);
	}

	@Route(path = "/tasks", type = POST)
	public int createTask(TaskDto taskDto) {
		return new Task(0, taskDto.name(), null, null, null, null, null, null).getId();
	}
	
	@Route(path = "/tasks/(\\d+)", type = PUT)
	public void editTask(TaskDto taskDto, @Argument Task task) {
        taskMapper.fullCopyDtoToModel(taskDto, task);
	}
	
	@Route(path = "/tasks/(\\d+)", type = PATCH)
	public void editPartialTask(TaskDto taskDto, @Argument Task task) {		
		taskMapper.safeCopyDtoToModel(taskDto, task);
	}
	
	@Route(path = "/tasks/(\\d+)", type = DELETE)
	public void deleteTask(@Argument Task task) {
		TreasureCache.delete(task);
	}
}

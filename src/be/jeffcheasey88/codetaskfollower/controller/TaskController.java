package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.PUT;

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
	
	private TaskRepository taskRepository;
	private TaskMapper taskMapper;
	
	public TaskController(@Injection TaskRepository taskRepository, @Injection TaskMapper taskMapper){
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
	}
	
    @Route(path = "/tasks", type = GET)
	public List<LightTaskDto> getTasks() {
    	return taskMapper.toLightDto(
			taskRepository.findAll()
		);
	}
	
	@Route(path = "/tasks/(\\d+)", type = GET)
	public TaskDto getTask(@Argument Task task) {
		return taskMapper.toDto(task);
	}

	/*@Route(path = "/tasks", type = POST)
	public int createTask(TaskDto taskDto) {
		return (new Task(0, taskDto.getName()).getId();
	}*/
	
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

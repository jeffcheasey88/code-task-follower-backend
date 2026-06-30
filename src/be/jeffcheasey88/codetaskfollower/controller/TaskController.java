package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Key;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TagRepository;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import be.jeffcheasey88.codetaskfollower.model.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.model.dto.TaskDto;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TaskController {
	@Injection private TaskRepository taskRepository;
	@Injection private TaskMapper taskMapper;
	
    @Route(path = "/tasks", type = GET)
	public List<LightTaskDto> getTasks() {
    	return taskMapper.toDto(
			taskRepository.findAll()
		);
	}
	
	@Route(path = "/tasks/(\\d+)", type = GET)
	public TaskDto getTask(Matcher matcher) {
		return taskMapper.toDto(
			taskRepository.findById(Integer.parseInt(matcher.group(1)))
		);
	}

	/*@Route(path = "/tasks", type = POST)
	public int createTask(TaskDto taskDto) {
		return (new Task(0, taskDto.getName()).getId();
	}*/
	
	@Route(path = "/tasks/(\\d+)", type = PUT)
	public void editTask(TaskDto taskDto, @Key Task task) {
        taskMapper.fullCopyDtoToEntity(taskDto, task);
	}
	
	@Route(path = "/tasks/(\\d+)", type = PATCH)
	public void editPartialTask(TaskDto taskDto, @Key Task task) {		
		taskMapper.safeCopyDtoToEntity(taskDto, task);
	}
	
	@Route(path = "/tasks/(\\d+)", type = DELETE)
	public void deleteTask(@Key Task task) {
		TreasureCache.delete(task);
	}
}

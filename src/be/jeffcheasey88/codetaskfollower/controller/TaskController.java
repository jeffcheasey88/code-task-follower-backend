package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;

public class TaskController{
	
	@Injection private TaskRepository taskRepository;
	@Injection private TaskMapper taskMapper;
	@Injection private TagMapper tagMapper;
	
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
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = PUT)
	public void addTaskTag(Matcher matcher) {
        TemporalRepository.INSTANCE.insertTaskTag(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = DELETE)
	public void removeTaskTag(Matcher matcher) {
        TemporalRepository.INSTANCE.removeTaskTag(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/tasks/(\\d+)/tags")
	public List<TagDto> getTags(Matcher matcher){
		return tagMapper.toDto(TemporalRepository.INSTANCE.selectTags(Integer.parseInt(matcher.group(1))));
	}
	
	@Route(path = "/tasks/(\\d+)/state/(\\d+)", type = PUT)
	public void updateTaskState(Matcher matcher){
        TemporalRepository.INSTANCE.updateTaskState(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
}

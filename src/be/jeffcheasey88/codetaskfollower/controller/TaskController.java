package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TaskController{
	
	@Injection private TaskRepository taskRepository;
	@Injection private TaskMapper taskMapper;
	@Injection private TagMapper tagMapper;
	@Injection("modelUpdater") private Locker<ModelUpdateDto> modelLocker;
	
    @Route(path = "/tasks", needLogin = true)
	public List<LightTaskDto> getTasks(){
    	return taskMapper.toLightDto(taskRepository.findAll()).stream().map(taskDto -> new LightTaskDto(taskDto.id(), taskDto.name(), taskDto.description(), getTags(taskDto.id()), null)).toList();
	}
	
	@Route(path = "/tasks/(\\d+)", needLogin = true)
	public TaskDto getTask(@Argument Task task){
		TaskDto taskDto = taskMapper.toDto(task);
		return new TaskDto(taskDto.id(), taskDto.name(), taskDto.description(), null, getTags(taskDto.id()), null, null, null, null, null);
	}

	@Route(path = "/tasks", type = POST, needLogin = true)
	public int createTask(TaskDto taskDto){
		Task task = new Task(0, taskDto.name(), taskDto.description(), null, null, null, null, null, null);
		TemporalRepository.INSTANCE.updateTaskState(task.getId(), taskDto.state().getId());
		modelLocker.pushValue(new ModelUpdateDto(task, "create"));
		return task.getId();
	}
	
	@Route(path = "/tasks/(\\d+)", type = PUT, needLogin = true)
	public void editTask(TaskDto taskDto, @Argument Task task) {
        taskMapper.fullCopyDtoToModel(taskDto, task);
        TemporalRepository.INSTANCE.updateTask(task);
        modelLocker.pushValue(new ModelUpdateDto(task, "update"));
	}
	
	@Route(path = "/tasks/(\\d+)", type = PATCH, needLogin = true)
	public void editPartialTask(TaskDto taskDto, @Argument Task task) {	
		taskMapper.safeCopyDtoToModel(taskDto, task);
		TemporalRepository.INSTANCE.updateTask(task);
		modelLocker.pushValue(new ModelUpdateDto(task, "update"));
	}
	
	@Route(path = "/tasks/(\\d+)", type = DELETE, needLogin = true)
	public void deleteTask(@Argument Task task) {
		TemporalRepository.INSTANCE.removeTaskAnyProject(task.getId());
		TemporalRepository.INSTANCE.removeTaskAnyTag(task.getId());
		TreasureCache.delete(task);
		modelLocker.pushValue(new ModelUpdateDto(task, "delete"));
	}
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = PUT, needLogin = true)
	public void addTaskTag(Matcher matcher) {
        TemporalRepository.INSTANCE.insertTaskTag(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = DELETE, needLogin = true)
	public void removeTaskTag(Matcher matcher) {
        TemporalRepository.INSTANCE.removeTaskTag(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/tasks/(\\d+)/tags", needLogin = true)
	public List<TagDto> getTags(Matcher matcher){
		return getTags(Integer.parseInt(matcher.group(1)));
	}
	
	public List<TagDto> getTags(int taskId){
		return tagMapper.toDto(TemporalRepository.INSTANCE.selectTags(taskId));
	}
	
	@Route(path = "/tasks/(\\d+)/state/(\\d+)", type = PUT, needLogin = true)
	public void updateTaskState(Matcher matcher){
        TemporalRepository.INSTANCE.updateTaskState(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
}

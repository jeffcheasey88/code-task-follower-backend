package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import be.jeffcheasey88.codetaskfollower.model.dto.TaskDto;
import dev.peerat.framework.routes.Route;

public class TaskController {
    /*@Route(path = "/tasks", type = GET)
	public List<Task> getTasks() {
		return TaskRepository.getAll();
	}
	
	@Route(path = "/tasks/(\\d+)", type = GET)
	public Task getTask(Matcher matcher) {
		return TaskRepository.get(Integer.parseInt(matcher.group(1)));
	}

//	@Route(path = "/tasks", type = POST)
//	public int createTask(TaskDto taskDto){
//		return (new Task(0, taskDto.getName(), taskDto.getColor())).getId();
//	}
	
	@Route(path = "/tasks/(\\d+)", type = PUT)
	public void editTask(Matcher matcher, TaskDto taskDto) {
        taskDto.setId(Integer.parseInt(matcher.group(1)));
		TaskRepository.edit(taskDto);
	}
	
//	@Route(path = "/tasks/(\\d+)", type = PATCH)
//	public void editPartialTask(Matcher matcher, TaskDto taskDto) {
//		Task task = TaskRepository.get(Integer.parseInt(matcher.group(1)));
//		if(taskDto.getName() != null) task.setName(taskDto.getName());
//	}
	
	@Route(path = "/tasks/(\\d+)", type = DELETE)
	public void deleteTask(Matcher matcher) {
		TaskRepository.delete(Integer.parseInt(matcher.group(1)));
	}*/
}

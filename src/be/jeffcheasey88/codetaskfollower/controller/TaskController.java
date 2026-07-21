package be.jeffcheasey88.codetaskfollower.controller;

import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.*;
import static be.jeffcheasey88.codetaskfollower.tmp.Permission.canAccess;
import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightTaskDto;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.repository.TaskRepository;
import be.jeffcheasey88.codetaskfollower.tmp.Permission;
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
	public List<LightTaskDto> getTasks(User user){
    	return taskRepository.findAll()
    			.stream()
    			.filter(task -> canReadTask(user, task.getId()))
    			.map(taskMapper::toLightDto)
    			.map(taskDto -> new LightTaskDto(taskDto.id(), taskDto.name(), taskDto.description(), taskDto.stateId(), getTags(taskDto.id()), null))
    			.toList();
	}
	
	@Route(path = "/tasks/(\\d+)", needLogin = true)
	public TaskDto getTask(User user, @Argument Task task){
		if(!canReadTask(user, task.getId())) throw new HttpError(403);
		TaskDto taskDto = taskMapper.toDto(task);
		return new TaskDto(taskDto.id(), taskDto.name(), taskDto.description(), taskDto.estimateSeconds(), TemporalRepository.INSTANCE.selectStateForTask(taskDto.id()).getId(), getTags(taskDto.id()).stream().map(t -> t.getId()).toList(), null, null, null, null, null);
	}

	@Route(path = "/tasks", type = POST, needLogin = true)
	public int createTask(User user, TaskDto taskDto){
		Task task = new Task(0, taskDto.name(), taskDto.description(), 0, null, null, null, null, null, null);
		Permission.giveAccess("Task", task.getId(), "player", user.getId(), Permission.PERM_ADMIN);
		updateTaskState(task.getId(), taskDto.stateId());
		modelLocker.pushValue(new ModelUpdateDto(task, "create"));
		return task.getId();
	}
	
	@Route(path = "/tasks/(\\d+)", type = PUT, needLogin = true)
	public void editTask(User user, TaskDto taskDto, @Argument Task task){
		if(!canUpdateTask(user, task.getId())) throw new HttpError(403);
        taskMapper.fullCopyDtoToModel(taskDto, task);
        updateTask(task);
        modelLocker.pushValue(new ModelUpdateDto(taskMapper.toDto(task), "update"));
	}
	
	@Route(path = "/tasks/(\\d+)", type = PATCH, needLogin = true)
	public void editPartialTask(User user, TaskDto taskDto, @Argument Task task){
		if(!canUpdateTask(user, task.getId())) throw new HttpError(403);
		taskMapper.safeCopyDtoToModel(taskDto, task);
		updateTask(task);
		modelLocker.pushValue(new ModelUpdateDto(taskMapper.toDto(task), "update"));
	}
	
	@Route(path = "/tasks/(\\d+)", type = DELETE, needLogin = true)
	public void deleteTask(User user, @Argument Task task) {
		if(!canDeleteTask(user, task.getId())) throw new HttpError(403);
		removeTaskAnyProject(task.getId());
		removeTaskAnyTag(task.getId());
		TreasureCache.delete(task);
		modelLocker.pushValue(new ModelUpdateDto(taskMapper.toDto(task), "delete"));
	}
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = PUT, needLogin = true)
	public void addTaskTag(User user, @Argument Task task, @Argument(2) Tag tag){
		if(!canAddElementTask(user, task.getId())) throw new HttpError(403);
        insertTaskTag(task, tag);
        modelLocker.pushValue(new ModelUpdateDto(taskMapper.toDto(task), "update"));
	}
	
	@Route(path = "/tasks/(\\d+)/tag/(\\d+)", type = DELETE, needLogin = true)
	public void removeTaskTags(User user, @Argument Task task, @Argument(2) Tag tag){
		if(!canAddElementTask(user, task.getId())) throw new HttpError(403);
        removeTaskTag(task, tag);
        modelLocker.pushValue(new ModelUpdateDto(taskMapper.toDto(task), "update"));
	}
	
	@Route(path = "/tasks/(\\d+)/tags", needLogin = true)
	public List<TagDto> getTags(User user, Matcher matcher){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!canReadTask(user, taskId)) throw new HttpError(403);
		return getTags(taskId);
	}
	
	public List<TagDto> getTags(int taskId){
		return tagMapper.toDto(selectTags(taskId));
	}
	
	@Route(path = "/tasks/(\\d+)/state/(\\d+)", type = PUT, needLogin = true)
	public void updateTaskStates(User user, Matcher matcher){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!canUpdateTask(user, taskId)) throw new HttpError(403);
        updateTaskState(taskId, Integer.parseInt(matcher.group(2)));
	}
	
	public static boolean canReadTask(User user, int taskId){
		return user.isAdmin() || Permission.canAccessTask(user.getId(), taskId,
				access -> true,
				(access, override)-> true
			);
	}
	
	public static boolean canAddElementTask(User user, int taskId){
		return user.isAdmin() || Permission.canAccessTask(user.getId(), taskId,
				access -> canAccess(access, Permission.PERM_ADD) || canAccess(access, Permission.PERM_ADMIN),
				(access, override)-> {
					if(override) return canAccess(access, Permission.PERM_ADMIN);
					return canAccess(access, Permission.PERM_ADD) || canAccess(access, Permission.PERM_ADMIN);
				}
			);
	}
	
	public static boolean canUpdateTask(User user, int taskId){
		return user.isAdmin() || Permission.canAccessTask(user.getId(), taskId,
				access -> canAccess(access, Permission.PERM_UPDATE) || canAccess(access, Permission.PERM_ADMIN),
				(access, override)-> {
					if(override) return canAccess(access, Permission.PERM_ADMIN);
					return canAccess(access, Permission.PERM_UPDATE) || canAccess(access, Permission.PERM_ADMIN);
				}
			);
	}
	
	public static boolean canDeleteTask(User user, int taskId){
		return user.isAdmin() || Permission.canAccessTask(user.getId(), taskId,
				access -> canAccess(access, Permission.PERM_DELETE) || canAccess(access, Permission.PERM_ADMIN),
				(access, override)-> {
					if(override) return canAccess(access, Permission.PERM_ADMIN);
					return canAccess(access, Permission.PERM_DELETE) || canAccess(access, Permission.PERM_ADMIN);
				}
			);
	}
	
	public static boolean canAdminTask(User user, int taskId){
		return user.isAdmin() || Permission.canAccessTask(user.getId(), taskId,
				access -> canAccess(access, Permission.PERM_ADMIN),
				(access, override)-> canAccess(access, Permission.PERM_ADMIN)
			);
	}
}

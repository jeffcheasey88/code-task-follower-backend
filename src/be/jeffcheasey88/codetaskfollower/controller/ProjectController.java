package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.mapper.ProjectMapper;
import be.jeffcheasey88.codetaskfollower.mapper.StateMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.repository.ProjectRepository;
import be.jeffcheasey88.codetaskfollower.repository.StateRepository;
import be.jeffcheasey88.codetaskfollower.tmp.Permission;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class ProjectController {
	@Injection private ProjectRepository projectRepository;
	@Injection private ProjectMapper projectMapper;
	@Injection private StateMapper stateMapper;
	@Injection private StateRepository stateRepository;
	@Injection private TaskMapper taskMapper;
	@Injection private TagMapper tagMapper;
	@Injection("modelUpdater") private Locker<ModelUpdateDto> modelLocker;
	
	@Route(path = "/projects", needLogin = true)
	public List<LightProjectDto> getProjects(User user){
		return projectMapper.toLightDto(
				projectRepository.findAll()
				.stream()
				.filter(project -> Permission.canAccessProject(user.getId(), project.getId(), i->true))
				.toList()
			);
	}
	
	@Route(path = "/projects/(\\d+)", needLogin = true)
	public ProjectDto getProject(User user, @Argument Project project){
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->true)) throw new HttpError(403);
		ProjectDto projectDto = projectMapper.toDto(project);
		ProjectDto p = new ProjectDto(projectDto.id(), projectDto.name(), projectDto.color(), projectDto.description(), getStates(projectDto.id()), getTasks(projectDto.id()));
		return p;
	}

	@Route(path = "/projects", type = POST, needLogin = true)
	public int createProject(User user, ProjectDto projectDto){
		Project project = new Project(0, projectDto.name(), projectDto.color(), projectDto.description());
		Permission.giveAccess("Project", project.getId(), "player", user.getId(), Permission.PERM_ADMIN);
		return project.getId();
	}
	
	// TODO : GET /projects/{id}/tasks/{stateId} 	récupere toutes les tâches d'un certain etat
	
	@Route(path = "/projects/(\\d+)", type = PUT, needLogin = true)
	public void editProject(User user, @Argument Project project, ProjectDto projectDto){
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->i%Permission.PERM_UPDATE != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
		project.setName(projectDto.name());
		project.setColor(projectDto.color());
		project.setDescription(projectDto.description());
		TemporalRepository.INSTANCE.updateProject(project);
		modelLocker.pushValue(new ModelUpdateDto(projectMapper.toDto(project), "update"));
	}
	
	@Route(path = "/projects/(\\d+)", type = PATCH, needLogin = true)
	public void editPartialProject(User user, @Argument Project project, ProjectDto projectDto) {
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->i%Permission.PERM_UPDATE != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
		if(projectDto.name() != null) project.setName(projectDto.name());
		if(projectDto.color() != null) project.setColor(projectDto.color());
		if(projectDto.description() != null) project.setDescription(projectDto.description());
		TemporalRepository.INSTANCE.updateProject(project);
		modelLocker.pushValue(new ModelUpdateDto(projectMapper.toDto(project), "update"));
	}
	
	@Route(path = "/projects/(\\d+)", type = DELETE, needLogin = true)
	public void deleteProject(User user, @Argument Project project){
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->i%Permission.PERM_DELETE != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
		TreasureCache.delete(project);
		modelLocker.pushValue(new ModelUpdateDto(projectMapper.toDto(project), "delete"));
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = PUT, needLogin = true)
	public void addProjectState(User user, @Argument Project project, @Argument(2) State state){
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->i%Permission.PERM_UPDATE != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
        TemporalRepository.INSTANCE.insertProjectStates(project, state);
        modelLocker.pushValue(new ModelUpdateDto(projectMapper.toDto(project), "update"));
	}

	@Route(path = "/projects/(\\d+)/task/(\\d+)", type = PUT, needLogin = true)
	public void addProjectTask(User user, Matcher matcher){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!Permission.canAccessProject(user.getId(), projectId, i->i%Permission.PERM_ADD != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
        TemporalRepository.INSTANCE.insertProjectTasks(projectId, Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = DELETE, needLogin = true)
	public void removeProjectState(User user, @Argument Project project, @Argument(2) State state){
		if(!Permission.canAccessProject(user.getId(), project.getId(), i->i%Permission.PERM_UPDATE != 0 || i%Permission.PERM_ADMIN!= 0)) throw new HttpError(403);
        TemporalRepository.INSTANCE.removeProjectStates(project, state);
        modelLocker.pushValue(new ModelUpdateDto(projectMapper.toDto(project), "delete"));
	}
	
	@Route(path = "/projects/(\\d+)/states", needLogin = true)
	public List<StateDto> getStates(User user, Matcher matcher){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!Permission.canAccessProject(user.getId(), projectId, i->true)) throw new HttpError(403);
		return getStates(projectId);
	}
	
	public List<StateDto> getStates(int projectId){
		return stateMapper.toDto(TemporalRepository.INSTANCE.selectStates(projectId));
	}

	public List<TaskDto> getTasks(int projectId){
		return taskMapper.toDto(TemporalRepository.INSTANCE.selectTasks(projectId)).stream().map(t -> {
			List<Tag> tags = TemporalRepository.INSTANCE.selectTags(t.id());
			List<Integer> tagIds = tags.stream().map(dto -> dto.getId()).toList();
			
			return new TaskDto(t.id(), t.name(), t.description(), stateMapper.toDto(TemporalRepository.INSTANCE.selectStateForTask(t.id())).getId(), tagIds, null, null, null, null, null);
		}).toList();
	}
}

package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;
import be.jeffcheasey88.codetaskfollower.dto.TaskDto;
import be.jeffcheasey88.codetaskfollower.mapper.ProjectMapper;
import be.jeffcheasey88.codetaskfollower.mapper.StateMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.mapper.TaskMapper;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.repository.ProjectRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class ProjectController {
	@Injection private ProjectRepository projectRepository;
	@Injection private ProjectMapper projectMapper;
	@Injection private StateMapper stateMapper;
	@Injection private TaskMapper taskMapper;
	@Injection private TagMapper tagMapper;
	
	@Route(path = "/projects", needLogin = true)
	public List<LightProjectDto> getProjects(){
		return projectMapper.toLightDto(projectRepository.findAll());
	}
	
	@Route(path = "/projects/(\\d+)", needLogin = true)
	public ProjectDto getProject(@Argument Project project) {
		ProjectDto projectDto = projectMapper.toDto(project);
		ProjectDto p = new ProjectDto(projectDto.id(), projectDto.name(), projectDto.color(), projectDto.description(), getStates(projectDto.id()), getTasks(projectDto.id()));

		return p;
	}

	@Route(path = "/projects", type = POST, needLogin = true)
	public int createProject(ProjectDto projectDto){
		return (new Project(0, projectDto.name(), projectDto.color(), projectDto.description())).getId();
	}
	
	// TODO : GET /projects/{id}/tasks/{stateId} 	récupere toutes les tâches d'un certain etat
	
	@Route(path = "/projects/(\\d+)", type = PUT, needLogin = true)
	public void editProject(@Argument Project project, ProjectDto projectDto) {
		project.setName(projectDto.name());
		project.setColor(projectDto.color());
	}
	
	@Route(path = "/projects/(\\d+)", type = PATCH, needLogin = true)
	public void editPartialProject(@Argument Project project, ProjectDto projectDto) {
		if(projectDto.name() != null) project.setName(projectDto.name());
		if(projectDto.color() != null) project.setColor(projectDto.color());
	}
	
	@Route(path = "/projects/(\\d+)", type = DELETE, needLogin = true)
	public void deleteProject(@Argument Project project) {
		TreasureCache.delete(project);
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = PUT, needLogin = true)
	public void addProjectState(Matcher matcher) {
        TemporalRepository.INSTANCE.insertProjectStates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	@Route(path = "/projects/(\\d+)/task/(\\d+)", type = PUT, needLogin = true)
	public void addProjectTask(Matcher matcher) {
        TemporalRepository.INSTANCE.insertProjectTasks(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = DELETE, needLogin = true)
	public void removeProjectState(Matcher matcher) {
        TemporalRepository.INSTANCE.removeProjectStates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/projects/(\\d+)/states", needLogin = true)
	public List<StateDto> getStates(Matcher matcher){
		return getStates(Integer.parseInt(matcher.group(1)));
	}
	
	public List<StateDto> getStates(int projectId){
		return stateMapper.toDto(TemporalRepository.INSTANCE.selectStates(projectId));
	}

	public List<TaskDto> getTasks(int projectId){
		return taskMapper.toDto(TemporalRepository.INSTANCE.selectTasks(projectId)).stream().map(t -> new TaskDto(t.id(), t.name(), stateMapper.toDto(TemporalRepository.INSTANCE.selectStateForTask(t.id())), tagMapper.toDto(TemporalRepository.INSTANCE.selectTags(t.id())), null, null, null, null, null)).toList();
	}
}

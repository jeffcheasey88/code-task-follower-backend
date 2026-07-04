package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.mapper.ProjectMapper;
import be.jeffcheasey88.codetaskfollower.mapper.StateMapper;
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
	
	@Route(path = "/projects")
	public List<LightProjectDto> getProjects(){
		return projectMapper.toLightDto(projectRepository.findAll());
	}
	
	@Route(path = "/projects/(\\d+)")
	public ProjectDto getProject(@Argument Project project) {
		ProjectDto projectDto = projectMapper.toDto(project);
		return new ProjectDto(projectDto.id(), projectDto.name(), projectDto.color(), projectDto.description());
	}

	@Route(path = "/projects", type = POST)
	public int createProject(ProjectDto projectDto){
		return (new Project(0, projectDto.name(), projectDto.color(), projectDto.description())).getId();
	}
	
	// TODO : GET /projects/{id}/tasks/{stateId} 	récupere toutes les tâches d'un certain etat
	
	@Route(path = "/projects/(\\d+)", type = PUT)
	public void editProject(@Argument Project project, ProjectDto projectDto) {
		project.setName(projectDto.name());
		project.setColor(projectDto.color());
	}
	
	@Route(path = "/projects/(\\d+)", type = PATCH)
	public void editPartialProject(@Argument Project project, ProjectDto projectDto) {
		if(projectDto.name() != null) project.setName(projectDto.name());
		if(projectDto.color() != null) project.setColor(projectDto.color());
	}
	
	@Route(path = "/projects/(\\d+)", type = DELETE)
	public void deleteProject(@Argument Project project) {
		TreasureCache.delete(project);
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = PUT)
	public void addProjectState(Matcher matcher) {
        TemporalRepository.INSTANCE.insertProjectStates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/projects/(\\d+)/state/(\\d+)", type = DELETE)
	public void removeProjectState(Matcher matcher) {
        TemporalRepository.INSTANCE.removeProjectStates(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}
	
	@Route(path = "/projects/(\\d+)/states")
	public List<StateDto> getStates(Matcher matcher){
		return getStates(Integer.parseInt(matcher.group(1)));
	}
	
	public List<StateDto> getStates(int projectId){
		return stateMapper.toDto(TemporalRepository.INSTANCE.selectStates(projectId));
	}
	
}

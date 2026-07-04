package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.mapper.ProjectMapper;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.repository.ProjectRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class ProjectController {
	@Injection private ProjectRepository projectRepository;
	@Injection private ProjectMapper projectMapper;
	
	@Route(path = "/projects")
	public List<LightProjectDto> getProjects(){
		return projectMapper.toLightDto(projectRepository.findAll());
	}
	
	@Route(path = "/projects/(\\d+)")
	public ProjectDto getProject(@Argument Project project) {
		return projectMapper.toDto(project);
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
}

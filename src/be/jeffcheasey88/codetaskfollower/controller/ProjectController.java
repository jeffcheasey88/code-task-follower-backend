package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.repository.ProjectRepository;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Key;
import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.mapper.ProjectMapper;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;

public class ProjectController {
	private ProjectRepository projectRepository;
	private ProjectMapper projectMapper;
	
	public ProjectController(@Injection ProjectRepository projectRepository, @Injection ProjectMapper projectMapper) {
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}
	
	@Route(path = "/projects", type = GET)
	public List<LightProjectDto> getProjects() {
		return projectMapper.toLightDto(
			projectRepository.findAll()
		);
	}
	
	@Route(path = "/projects/(\\d+)", type = GET)
	public ProjectDto getProject(@Key Project project) {
		return projectMapper.toDto(project);
	}

	/*@Route(path = "/projects", type = POST)
	public int createProject(ProjectDto projectDto){
		return (new Project(0, projectDto.getName(), projectDto.getColor())).getId();
	}
	
	// TODO : GET /projects/{id}/tasks/{stateId} 	récupere toutes les tâches d'un certain etat
	
	
	@Route(path = "/projects/(\\d+)", type = PUT)
	public void editProject(Matcher matcher, ProjectDto projectDto) {
		Project project = ProjectRepository.get(Integer.parseInt(matcher.group(1)));
		project.setName(projectDto.getName());
		project.setColor(projectDto.getColor());
	}
	
	@Route(path = "/projects/(\\d+)", type = PATCH)
	public void editPartialProject(Matcher matcher, ProjectDto projectDto) {
		Project project = ProjectRepository.get(Integer.parseInt(matcher.group(1)));
		if(projectDto.getName() != null) project.setName(projectDto.getName());
		if(projectDto.getColor() != null) project.setColor(projectDto.getColor());
	}
	
	@Route(path = "/projects/(\\d+)", type = DELETE)
	public void deleteProject(Matcher matcher) {
		ProjectRepository.delete(Integer.parseInt(matcher.group(1)));
	}*/
}

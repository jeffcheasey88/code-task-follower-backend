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
import be.jeffcheasey88.codetaskfollower.model.dto.ProjectDto;
import dev.peerat.framework.routes.Route;

public class ProjectController{

	@Route(path = "/projects", type = GET)
	public List<Project> getProjects(){
		return ProjectRepository.getAll();
	}
	
	@Route(path = "/projects/(\\d+)", type = GET)
	public Project getProject(Matcher matcher, ProjectDto projectDto){
		return ProjectRepository.get(Integer.parseInt(matcher.group(1)));
	}

	/*@Route(path = "/projects", type = POST)
	public int createProject(ProjectDto projectDto){
		return (new Project(0, projectDto.getName(), projectDto.getColor())).getId();
	}*/
	
	//POST /project/						crée un projet
	//PUT /project/{id}						edit un projet
	//GET /project/{id}						récupere un projet
	//GET /project/{id}/tasks/{stateId} 	récupere toutes les tâches d'un certain etat
	
	
	@Route(path = "/projects/(\\d+)", type = PUT)
	public void editProject(Matcher matcher, ProjectDto projectDto){
		Project project = ProjectRepository.get(Integer.parseInt(matcher.group(1)));
		project.setName(projectDto.getName());
		project.setColor(projectDto.getColor());
	}
	
	@Route(path = "/projects/(\\d+)", type = PATCH)
	public void editPartialProject(Matcher matcher, ProjectDto projectDto){
		Project project = ProjectRepository.get(Integer.parseInt(matcher.group(1)));
		if(projectDto.getName() != null) project.setName(projectDto.getName());
		if(projectDto.getColor() != null) project.setColor(projectDto.getColor());
	}
	
	@Route(path = "/projects/(\\d+)", type = DELETE)
	public void deleteProject(Matcher matcher){
		ProjectRepository.delete(Integer.parseInt(matcher.group(1)));
	}

}

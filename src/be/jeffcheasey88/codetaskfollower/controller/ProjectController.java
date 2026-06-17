package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.PUT;

import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.dto.ProjectUpdateDto;
import dev.peerat.framework.routes.Route;

public class ProjectController{
	
	@Route(path = "/projects/(\\d+)", type = PUT)
	public void editTag(Matcher matcher, ProjectUpdateDto projectDto){
		Project project = Project.getProject(Integer.parseInt(matcher.group(1)));
		project.setName(projectDto.getName());
		project.setColor(projectDto.getColor());
	}

}

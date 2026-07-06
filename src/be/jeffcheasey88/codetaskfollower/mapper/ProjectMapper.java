package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.model.Project;
import dev.peerat.framework.dependency.Injection;

@Injection
public class ProjectMapper extends Mapper<ProjectDto, LightProjectDto, Project> {
	@Injection private StateMapper stateMapper;
	@Injection private TaskMapper taskMapper;

	@Override
	public ProjectDto toDto(Project model) {
		return new ProjectDto(
			model.getId(),
			model.getName(),
			model.getColor(),
			model.getDescription(),
			stateMapper.toDto(model.getStates())
		);
	}

	@Override
	public LightProjectDto toLightDto(Project model) {
		return new LightProjectDto(model.getId(), model.getName(), model.getColor(), model.getDescription());
	}

	@Override
	public void fullCopyDtoToModel(ProjectDto dto, Project model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void safeCopyDtoToModel(ProjectDto dto, Project model) {
		// TODO Auto-generated method stub
		
	}
}

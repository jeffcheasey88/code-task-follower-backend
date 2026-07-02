package be.jeffcheasey88.codetaskfollower.mapper;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.dto.LightProjectDto;
import be.jeffcheasey88.codetaskfollower.dto.ProjectDto;
import be.jeffcheasey88.codetaskfollower.model.Project;
import dev.peerat.framework.dependency.Injection;

@Injection
public class ProjectMapper extends Mapper<ProjectDto, LightProjectDto, Project> {
	private StateMapper stateMapper;
	private TaskMapper taskMapper;

	public ProjectMapper(@Injection StateMapper stateMapper, @Injection TaskMapper taskMapper) {
		this.stateMapper = stateMapper;
		this.taskMapper = taskMapper;
	}
	
	@Override
	public ProjectDto toDto(Project model) {
		return new ProjectDto(
			model.getId(),
			model.getName(),
			model.getColor(),
			stateMapper.toDto(model.getStates()),
			taskMapper.toDto(model.getTasks()),
			List.of()
		);
	}

	@Override
	public LightProjectDto toLightDto(Project model) {
		return new LightProjectDto(model.getId(), model.getName(), model.getColor());
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

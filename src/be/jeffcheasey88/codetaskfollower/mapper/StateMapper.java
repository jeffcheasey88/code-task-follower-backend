package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.State;
import dev.peerat.framework.dependency.Injection;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;

@Injection
public class StateMapper extends Mapper<StateDto, StateDto, State> {
	@Override
	public StateDto toDto(State model) {
		return new StateDto(model.getId(), model.getName(), model.getColor());
	}

	@Override
	public StateDto toLightDto(State model) {
		return toDto(model);
	}

	@Override
	public void fullCopyDtoToModel(StateDto dto, State model) {
		model.setName(dto.getName());
		model.setColor(dto.getColor());
	}

	@Override
	public void safeCopyDtoToModel(StateDto dto, State model) {
		if(dto.getName() != null) model.setName(dto.getName());
		if(dto.getColor() != null) model.setColor(dto.getColor());
	}
}

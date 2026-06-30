package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.dto.StateDto;

public class StateMapper extends Mapper<StateDto, StateDto, State> {
	@Override
	public StateDto toDto(State entity) {
		return new StateDto(entity.getId(), entity.getName(), entity.getColor());
	}

	@Override
	public StateDto toLightDto(State entity) {
		return toDto(entity);
	}

	@Override
	public void fullCopyDtoToEntity(StateDto dto, State entity) {
		entity.setName(dto.getName());
		entity.setColor(dto.getColor());
	}

	@Override
	public void safeCopyDtoToEntity(StateDto dto, State entity) {
		if(dto.getName() != null) entity.setName(dto.getName());
		if(dto.getColor() != null) entity.setColor(dto.getColor());
	}
}

package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Key;
import be.jeffcheasey88.codetaskfollower.mapper.StateMapper;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;
import be.jeffcheasey88.codetaskfollower.repository.StateRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class StateController {
	@Injection private StateRepository stateRepository;
	@Injection private StateMapper stateMapper;
	
	@Route(path = "/states", type = GET)
	public List<StateDto> getStates() {
		return stateMapper.toDto(stateRepository.findAll());
	}

	@Route(path = "/states", type = POST)
	public void createState(StateDto stateDto) {
		new State(0, stateDto.getName(), stateDto.getColor());
	}
	
	@Route(path = "/states/(\\d+)", type = PUT)
	public void editState(StateDto stateDto, @Key State state){
		stateMapper.fullCopyDtoToModel(stateDto, state);
	}
	
	@Route(path = "/states/(\\d+)", type = PATCH)
	public void editPartialState(StateDto stateDto, @Key State state){
		stateMapper.safeCopyDtoToModel(stateDto, state);
	}
	
	@Route(path = "/states/(\\d+)", type = DELETE)
	public void deleteState(@Key State state){
		TreasureCache.delete(state);
	}
}

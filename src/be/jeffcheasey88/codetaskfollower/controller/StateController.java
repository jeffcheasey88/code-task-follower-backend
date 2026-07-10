package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;
import be.jeffcheasey88.codetaskfollower.dto.StateDto;
import be.jeffcheasey88.codetaskfollower.mapper.StateMapper;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.repository.StateRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class StateController {
	
	@Injection private StateRepository stateRepository;
	@Injection private StateMapper stateMapper;
	@Injection("modelUpdater") private Locker<ModelUpdateDto> modelLocker;
	
	@Route(path = "/states", needLogin = true)
	public List<StateDto> getStates() {
		return stateMapper.toDto(stateRepository.findAll());
	}

	@Route(path = "/states", type = POST, needLogin = true)
	public int createState(StateDto stateDto) {
		return new State(0, stateDto.getName(), stateDto.getColor()).getId();
	}
	
	@Route(path = "/states/(\\d+)", type = PUT, needLogin = true)
	public void editState(StateDto stateDto, @Argument State state){
		stateMapper.fullCopyDtoToModel(stateDto, state);
		TemporalRepository.INSTANCE.updateState(state);
	}
	
	@Route(path = "/states/(\\d+)", type = PATCH, needLogin = true)
	public void editPartialState(StateDto stateDto, @Argument State state){
		stateMapper.safeCopyDtoToModel(stateDto, state);
		TemporalRepository.INSTANCE.updateState(state);
	}
	
	@Route(path = "/states/(\\d+)", type = DELETE, needLogin = true)
	public void deleteState(@Argument State state){
		TreasureCache.delete(state);
	}
}

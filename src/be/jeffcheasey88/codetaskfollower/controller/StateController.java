package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.dto.StateDto;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class StateController{
	
	@Route(path = "/states", type = GET)
	public List<State> getStates(){
		return State.getStates();
	}

	@Route(path = "/states", type = POST)
	public void createState(StateDto stateDto){
		new State(0, stateDto.getName(), stateDto.getColor());
	}
	
	@Route(path = "/states/(\\d+)", type = PUT)
	public void editState(Matcher matcher, StateDto stateDto){
		State state = State.getState(Integer.parseInt(matcher.group(1)));
		state.setName(stateDto.getName());
		state.setColor(stateDto.getColor());
	}
	
	@Route(path = "/states/(\\d+)", type = DELETE)
	public void deleteState(Matcher matcher){
		TreasureCache.delete(State.getState(Integer.parseInt(matcher.group(1))));
	}

}

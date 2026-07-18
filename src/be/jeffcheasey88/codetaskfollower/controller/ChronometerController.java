package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.ChronometerDto;
import be.jeffcheasey88.codetaskfollower.dto.ChronometerPartDto;
import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.mapper.ChronometerMapper;
import be.jeffcheasey88.codetaskfollower.mapper.ChronometerPartMapper;
import be.jeffcheasey88.codetaskfollower.model.Chronometer;
import be.jeffcheasey88.codetaskfollower.model.ChronometerPart;
import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class ChronometerController{
	
	//POST chronometer/{taskId}								nouveau chornometer
	//GET chronometer/{taskId}								get un chronometer existant (avec les parts)
	//POST chronometer/{taskId}/part						crée une nouvelle part
	//PUT chronometer/{taskId}/part/{partId)				edit une part
	//DELETE chronometer/{taskId}/part/{partId)				supprime une part
	//POST chronometer/{taskId/part/{partId}/takeTiming 	prend le temps d'une part
	@Injection private ChronometerMapper chronometerMapper;
	@Injection private ChronometerPartMapper chronometerPartMapper;

	@Route(path = "chronometer/(\\d+)", type = POST, needLogin = true)
	public int createChornometer(User user, @Argument Task task){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		Chronometer chronometer = new Chronometer(0, 0, task.getId(), null);
		TemporalRepository.INSTANCE.updateChronometerTask(chronometer.getId(), task.getId());
		return chronometer.getId();
	}
	
	@Route(path = "chronometer/(\\d+)", needLogin = true)
	public ChronometerDto getChronometer(User user, @Argument Task task){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		return chronometerMapper.toDto(getChronometer(task.getId()));
	}
	
	@Route(path = "chronometer/(\\d+)/part", type = POST, needLogin = true)
	public int createChronometerPart(User user, @Argument Task task){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		Chronometer chronometer = getChronometer(task.getId());
		ChronometerPart chronometerPart = new ChronometerPart(0, 0, null, chronometer.getId());
		TemporalRepository.INSTANCE.updateChronometerPart(chronometerPart.getId(), chronometerPart.getChronometerId());
		return chronometerPart.getId();
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)", type = PUT, needLogin = true)
	public void editPart(User user, @Argument Task task, @Argument(2) ChronometerPart chronometerPart, ChronometerPartDto partDto){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		chronometerPartMapper.fullCopyDtoToModel(partDto, chronometerPart);
		TemporalRepository.INSTANCE.updateChronometerPart(chronometerPart);
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)", type = DELETE, needLogin = true)
	public void deletePart(User user, @Argument Task task, @Argument(2) ChronometerPart chronometerPart){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		TreasureCache.delete(chronometerPart);
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)/takeTiming", type = POST, needLogin = true)
	public ChronometerPartDto takeTiming(User user, @Argument Task task, @Argument(2) ChronometerPart chronometerPart){
		if(!TaskController.canAddElementTask(user, task.getId())) throw new HttpError(403);
		return null;
	}
	
	public Chronometer getChronometer(int taskId){
		return TreasureCache.<Chronometer>selectAll().filter(chrono -> chrono.getTaskId() == taskId).get();
	}
}

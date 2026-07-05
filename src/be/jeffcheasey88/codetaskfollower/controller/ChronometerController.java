package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import be.jeffcheasey88.codetaskfollower.model.ChronometerPart;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.ChronometerDto;
import be.jeffcheasey88.codetaskfollower.dto.ChronometerPartDto;
import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.framework.routes.Route;

public class ChronometerController{
	
	//POST chronometer/{taskId}								nouveau chornometer
	//GET chronometer/{taskId}								get un chronometer existant (avec les parts)
	//POST chronometer/{taskId}/part						crée une nouvelle part
	//PUT chronometer/{taskId}/part/{partId)				edit une part
	//DELETE chronometer/{taskId}/part/{partId)				supprime une part
	//POST chronometer/{taskId/part/{partId}/takeTiming 	prend le temps d'une part

	@Route(path = "chronometer/(\\d+)", type = POST, needLogin = true)
	public int createChornometer(@Argument Task task){
		return 0;
	}
	
	@Route(path = "chronometer/(\\d+)", needLogin = true)
	public ChronometerDto getChronometer(@Argument Task task){
		return null;
	}
	
	@Route(path = "chronometer/(\\d+)/part", type = POST, needLogin = true)
	public int createChronometerPart(@Argument Task task){
		return 0;
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)", type = PUT, needLogin = true)
	public ChronometerPartDto editPart(@Argument Task task, @Argument(2) ChronometerPart chronometerPart){
		return null;
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)", type = DELETE, needLogin = true)
	public void deletePart(@Argument Task task, @Argument(2) ChronometerPart chronometerPart){
		
	}
	
	@Route(path = "chronometer/(\\d+)/part/(\\d+)/takeTiming", type = POST, needLogin = true)
	public ChronometerPartDto takeTiming(@Argument Task task, @Argument(2) ChronometerPart chronometerPart){
		return null;
	}
}
